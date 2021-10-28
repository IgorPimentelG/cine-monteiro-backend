package com.cine.monteiro.domain.model.user;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente extends Usuario {
	
	public Cliente(String CPF, String nome, String telefone, LocalDate dataNascimento, String email, String password) {
		super(CPF, nome, telefone, dataNascimento, email, password);
	}
	
	public Cliente() { }
	
}
