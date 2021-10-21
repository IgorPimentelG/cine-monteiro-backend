package com.cine.monteiro.model.users;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.br.*;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_USUARIO")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@CPF
	@NotBlank
	@Column(unique = true)
	private String CPF;
	
	@NotBlank
	private String nome;
	
	@Column(unique = true)
	private String telefone;
	
	@Column(name = "data_nascimento", columnDefinition = "DATE", nullable = false)
	private LocalDate dataNascimento;
	
	@Email
	@NotBlank
	@Column(unique = true)
	private String email;
	
	@NotBlank
	private String password;
	
	// Construtores
	public Usuario() { }

	public Usuario(String CPF, String nome, String telefone, LocalDate dataNascimento, String email, String password) {
		this.CPF = CPF;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
	}

}
