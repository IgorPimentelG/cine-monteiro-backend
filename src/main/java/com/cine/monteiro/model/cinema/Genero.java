package com.cine.monteiro.model.cinema;

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
	
	public String toString() {
		return genero;
	}

}
