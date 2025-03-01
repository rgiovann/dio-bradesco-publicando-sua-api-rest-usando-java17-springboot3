package com.portalsocial.portalsocial.controller;

import java.util.List;

import com.portalsocial.portalsocial.api.dto.ProfessorDto;
import com.portalsocial.portalsocial.api.input.CursoNomeInput;
import com.portalsocial.portalsocial.domain.model.Categoria;
import com.portalsocial.portalsocial.domain.model.Curso;
import com.portalsocial.portalsocial.service.CadastroCategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portalsocial.portalsocial.api.dto.CursoCompactDto;
import com.portalsocial.portalsocial.api.dto.CursoDto;
import com.portalsocial.portalsocial.service.CadastroCursoService;

@RestController
@RequestMapping(value = "/cursos")
public class CursoController {

	private CadastroCursoService cursoService;
	private CadastroCategoriaService categoriaService;

	public CursoController(CadastroCursoService cursoService,CadastroCategoriaService categoria) {

		this.cursoService        = cursoService;
		this.categoriaService    = categoria;
	}


//	@GetMapping
//	public List<CursoCompactDto> listar() {
//
//		return cursoService.listar();
//
//	}

	@GetMapping
	public ResponseEntity<List<CursoCompactDto>> listar() {
		return ResponseEntity.ok().body(cursoService.listar());
	}

	@GetMapping("/{cursoId}")
	public ResponseEntity<CursoDto> buscar(@PathVariable Long cursoId) {

		return ResponseEntity.ok().body(new CursoDto(cursoService.buscarOuFalhar(cursoId)));

	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CursoDto adicionar(@RequestBody @Valid CursoNomeInput cursoNomeInput)
	{
		Categoria categoria = categoriaService.buscarOuFalhar(cursoNomeInput.getCategoria().getId());

		Curso curso = cursoNomeInput.fromCursoInputToEntity(cursoNomeInput,null, new Curso());
		  curso.setCategoria(categoria);
		  curso = cursoService.salvar(curso);

		  return new CursoDto(curso);
	}


	@PutMapping("/{cursoId}")
	 public CursoDto atualizar(@PathVariable Long cursoId, @RequestBody @Valid  CursoNomeInput cursoNomeInput)
	 {
		 Categoria categoria = categoriaService.buscarOuFalhar(cursoNomeInput.getCategoria().getId());
		 Curso curso = cursoService.buscarOuFalhar(cursoId);

		 curso = cursoNomeInput.fromCursoInputToEntity(cursoNomeInput,cursoId, curso);
		 curso.setCategoria(categoria);
		 curso = cursoService.salvar(curso);

		 return new CursoDto(curso);

	 }
	@DeleteMapping("/{cursoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cursoId) {
		cursoService.excluir(cursoId);
	 }

	/*
	 * @PutMapping("/{estadoId}")
	 * public CursoDto atualizar(@PathVariable Long estadoId, @RequestBody @Valid
	 * CursoNomeInput CursoNomeInput)
	 * {
	 * Curso estado = estadoService.buscarOuFalhar(estadoId);
	 * 
	 * CursoNomeInputDisassembler.copyToEntity(CursoNomeInput,estado);
	 * 
	 * return CursoDtoAssembler.toDto( estadoService.salvar(estado));
	 * 
	 * }
	 * 

	 * 
	 */
}
