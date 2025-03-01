package com.portalsocial.portalsocial.domain.exception;

import java.io.Serial;

public class ProfessorNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	@Serial
	private static final long serialVersionUID = 1L;

	public ProfessorNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ProfessorNaoEncontradoException(Long estadoId) {
		// chamando o construtor anterior;
		 this(String.format("Professor de código %d não encontrado.",estadoId));
	}


}
