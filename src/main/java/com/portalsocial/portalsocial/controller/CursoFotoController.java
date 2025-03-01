package com.portalsocial.portalsocial.controller;

import com.portalsocial.portalsocial.api.dto.FotoCursoDto;
import com.portalsocial.portalsocial.api.input.FotoCursoInput;
import com.portalsocial.portalsocial.assembler.FotoCursoDtoAssembler;
import com.portalsocial.portalsocial.domain.exception.EntidadeNaoEncontradaException;
import com.portalsocial.portalsocial.domain.model.Curso;
import com.portalsocial.portalsocial.domain.model.FotoCurso;
import com.portalsocial.portalsocial.service.CadastroCursoService;
import com.portalsocial.portalsocial.service.FotoCursoService;
import com.portalsocial.portalsocial.service.FotoStorageService;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/cursos/{cursoId}/foto")
public class CursoFotoController {
    private final FotoCursoService catalogoFotoService;
    private final CadastroCursoService  cadastroCursoService;
    private final FotoStorageService fotoStorageService;
    private final FotoCursoDtoAssembler fotoCursoDtoAssembler;


    public CursoFotoController(FotoCursoService catalogoFotoProdutoService, CadastroCursoService cadastroCursoService, FotoStorageService fotoStorageService, FotoCursoDtoAssembler fotoCursoDtoAssembler) {
        this.catalogoFotoService = catalogoFotoProdutoService;
        this.cadastroCursoService = cadastroCursoService;
        this.fotoStorageService = fotoStorageService;
        this.fotoCursoDtoAssembler = fotoCursoDtoAssembler;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoCursoDto   atualizarFoto(@PathVariable Long cursoId,
                                        @Valid FotoCursoInput  fotoProdutoInput,
                                        @RequestPart(required=true) MultipartFile arquivo) throws IOException {

        Curso curso = cadastroCursoService.buscarOuFalhar(cursoId);

        // ja vem no request
        //MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoCurso foto = new FotoCurso();
        foto.setCurso(curso);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoCurso fotoSalva = catalogoFotoService.salvar(foto, arquivo.getInputStream());

        return fotoCursoDtoAssembler.toDto(fotoSalva);

    }

    // Accept = application/json
    // Accept = application/json AND Accept = image/*
     @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
     public FotoCursoDto  consultarFoto(@PathVariable Long cursoId ){

        FotoCurso fotoCurso = catalogoFotoService.buscarOuFalhar(cursoId );

        return fotoCursoDtoAssembler.toDto(fotoCurso);

    }

    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servirFoto(@PathVariable Long cursoId,
                                        @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {

            // debug on
            //System.out.println("DEBUG 0001 ====== >> acceptHeader = " + acceptHeader );
            // debug off

            FotoCurso fotoCurso = catalogoFotoService.buscarOuFalhar(cursoId);

            // debug on
            //System.out.println("DEBUG 0002 ====== >> fotoProduto.getContentType() = " + fotoProduto.getContentType() );
            // debug off

            MediaType  mediaTypeFoto = MediaType.parseMediaType(fotoCurso.getContentType());

            List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypeAceitas);

            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoCurso.getNomeArquivo());

            if(fotoRecuperada.temUrl())
            {
                return  ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            }
            else
            {
                return ResponseEntity
                        .ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        }
        catch(EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }



    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirFoto(@PathVariable Long cursoId)
    {
        FotoCurso fotoCurso = catalogoFotoService.buscarOuFalhar(cursoId);
        catalogoFotoService.excluir(fotoCurso);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException
    {

        // debug on
        //System.out.println("DEBUG 0003 ====== >> mediaTypeFoto.getType() = " + mediaTypeFoto.getType() );
        // debug off

        // debug on
        //for (MediaType mt : mediaTypeAceitas) {
        //
        //	 System.out.println("DEBUG 0004 ====== >> for each mt.getType() = " + mt.getType() );
        //
        //}
        // debug off

        boolean compativel = mediaTypeAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if(!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }

    }

}
