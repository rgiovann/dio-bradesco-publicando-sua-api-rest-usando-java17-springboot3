package com.portalsocial.portalsocial.domain.exception;

import java.io.Serial;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(String msg) {
		super(msg);
	}

}

