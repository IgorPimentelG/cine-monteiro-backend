package com.cine.monteiro.domain.model.user;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.br.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_USER")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

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

	private String authorities;							// USER, ADMIN
	
	// Construtores
	public User() { }

	public User(String CPF, String nome, String telefone, LocalDate dataNascimento, String email, String password, String authorities) {
		this.CPF = CPF;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.authorities = authorities;
	}
	
	public String toString() {
		return "\nID: " + this.id +
				"\nCPF: " + this.CPF + 
				"\nNome: " + this.nome + 
				"\nEmail: " + this.email + 
				"\nTelefone: " + this.telefone + 
				"\nData de Nascimento: " + this.dataNascimento;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(this.authorities.split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
	
	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
