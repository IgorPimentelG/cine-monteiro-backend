package com.cine.monteiro.model.users;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "TB_ADMINISTRADOR")
public class Administrador extends Usuario {

	public Administrador(String CPF, String nome, String telefone, Date dataNascimento, String email, String password) {
		super(CPF, nome, telefone, dataNascimento, email, password);
	}
	
	public Administrador() { }
	
}
