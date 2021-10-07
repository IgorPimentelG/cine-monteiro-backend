package com.cine.monteiro.model.users;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_USUARIO")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Column(unique = true)
	private String CPF;
	
	private String nome;
	
	@Column(unique = true)
	private String telefone;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	public Usuario() { }

	public Usuario(String CPF, String nome, String telefone, Date dataNascimento, String email, String password) {
		this.CPF = CPF;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
	}

}
