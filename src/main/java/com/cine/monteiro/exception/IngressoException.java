package com.cine.monteiro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IngressoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public IngressoException(String mensagem) {
		super("[ERROR INGRESSO] - " + mensagem);
	}

}
