package com.portalsocial.portalsocial.domain.exception;

import java.io.Serial;

public class NegocioException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public NegocioException(String msg) {
		super(msg);
	}
	
	public NegocioException(String msg, Throwable cause) {
		super(msg, cause);
	}

}

