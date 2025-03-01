package com.portalsocial.portalsocial.service;

import com.portalsocial.portalsocial.domain.exception.FotoNaoEncontradaException;
import com.portalsocial.portalsocial.domain.model.FotoCurso;
import com.portalsocial.portalsocial.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;
@Service
public class FotoCursoService {
    private final CursoRepository cursoRepository;

    private final FotoStorageService fotoStorageService;

    public FotoCursoService(CursoRepository cursoRepository, FotoStorageService fotoStorageService) {
        this.cursoRepository = cursoRepository;
        this.fotoStorageService = fotoStorageService;
    }

    @Transactional
    public FotoCurso salvar(FotoCurso foto, InputStream dadosArquivo) {

        Long cursoId = foto.getCursoId();

        String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());

        String nomeArquivoExistente = null;

        foto.setNomeArquivo(novoNomeArquivo);

        Optional<FotoCurso> fotoExistente = cursoRepository.findFotoById(cursoId);

        if (fotoExistente.isPresent()) {

            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            cursoRepository.delete(fotoExistente.get());
        }

        foto = cursoRepository.save(foto);
        cursoRepository.flush(); // salva no BD os dados da foto, commitando a insercao ANTES de salvar a foto.

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(novoNomeArquivo)
                .inputStream(dadosArquivo)
                .contentType(foto.getContentType())
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;

    }

    public FotoCurso buscarOuFalhar(Long cursoId ) {

        return  cursoRepository.findFotoById(cursoId)
                .orElseThrow(() -> new FotoNaoEncontradaException(cursoId) );
    }

    @Transactional
    public void excluir(FotoCurso foto) {

        cursoRepository.delete(foto);
        cursoRepository.flush(); // salva no BD os dados da foto, commitando a insercao ANTES de remover a foto.
        fotoStorageService.remover(foto.getNomeArquivo());
    }

}
