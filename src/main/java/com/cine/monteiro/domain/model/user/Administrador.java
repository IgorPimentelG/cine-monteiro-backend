package com.cine.monteiro.domain.model.user;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "TB_ADMINISTRADOR")
public class Administrador extends Usuario {

	public Administrador(String CPF, String nome, String telefone, LocalDate dataNascimento, String email, String password) {
		super(CPF, nome, telefone, dataNascimento, email, password);
	}
	
	public Administrador() { }
	
}
