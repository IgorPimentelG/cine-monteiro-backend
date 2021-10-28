package com.cine.monteiro.domain.model.user;

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
	
	@Column(unique = true, nullable = false)
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
	
	public String toString() {
		return "\nID: " + this.id +
				"\nCPF: " + this.CPF + 
				"\nNome: " + this.nome + 
				"\nEmail: " + this.email + 
				"\nTelefone: " + this.telefone + 
				"\nData de Nascimento: " + this.dataNascimento;
	}

}
