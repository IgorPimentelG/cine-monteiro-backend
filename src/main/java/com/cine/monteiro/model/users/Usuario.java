package com.cine.monteiro.model.users;

import java.util.Date;

import lombok.Data;

@Data
public abstract class Usuario {
	
	private String nome;
	private String CPF;
	private String telefone;
	private Date dataNascimento;
	private String email;
	private String senha;

}
