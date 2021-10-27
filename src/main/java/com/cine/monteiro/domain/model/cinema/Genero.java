package com.cine.monteiro.domain.model.cinema;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_GENERO")
public class Genero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true, nullable = false)
	private String genero;
	
	@Column(nullable = true)
	private String descricao;
	
	// Construtores
	public Genero() { }
	
	public Genero(String genero, String descricao) {
		this.genero = genero;
		this.descricao = descricao;
	}
	
	public String toString() {
		return genero;
	}

}
