package com.cine.monteiro.exception;

public class FilmeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FilmeException(String mensagem) {
		super("[ERROR FILME] - " + mensagem);
	}

}
