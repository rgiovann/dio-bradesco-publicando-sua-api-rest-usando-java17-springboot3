package com.portalsocial.portalsocial.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portalsocial.portalsocial.domain.exception.EntidadeEmUsoException;
import com.portalsocial.portalsocial.api.dto.CursoCompactDto;
import com.portalsocial.portalsocial.domain.exception.CursoNaoEncontradoException;
import com.portalsocial.portalsocial.domain.model.Curso;
import com.portalsocial.portalsocial.repository.CursoRepository;

@Service
public class CadastroCursoService {

	private static final String MSG_CURSOEM_USO = "Curso de código %d não pode ser removido, pois está em uso.";
	private final CursoRepository cursoRepository;

	public CadastroCursoService(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;

	}

 
	public List<CursoCompactDto> listar() {

		List<Curso> cursos = cursoRepository.findAll();


		/*
		Quando você usa cursos.stream().map(CursoCompactDto::new), o método map irá percorrer cada elemento do
		stream de cursos e chamar o construtor CursoCompactDto(Curso curso) para criar uma instância de
		CursoCompactDto para cada objeto Curso no stream. Dessa forma, o método de referência sabe que deve
		passar o objeto Curso como parâmetro para o construtor da classe CursoCompactDto com base na assinatura do construtor.
		 */

		//return cursos.stream().map(x -> new CursoCompactDto(x)).collect(Collectors.toList());
		return cursos.stream().map(CursoCompactDto::new).collect(Collectors.toList());


	}

	@Transactional
	public Curso salvar(Curso curso) {

		return cursoRepository.save(curso);
	}

	@Transactional
	public void excluir(Long cursoId) {

		this.buscarOuFalhar(cursoId);
		try {
			System.out.println("Deletou aqui!");
			cursoRepository.deleteById(cursoId);

			cursoRepository.flush();
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CURSOEM_USO, cursoId));
		}

	}

	public Curso buscarOuFalhar(Long CursoId) {
		return cursoRepository.findById(CursoId).orElseThrow(() -> new CursoNaoEncontradoException(CursoId));
	}

}