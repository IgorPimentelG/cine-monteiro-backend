package com.cine.monteiro.model.users;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente extends Usuario {
	
	public Cliente(String CPF, String nome, String telefone, Date dataNascimento, String email, String password) {
		super(CPF, nome, telefone, dataNascimento, email, password);
	}
	
	public Cliente() { }
	
}
