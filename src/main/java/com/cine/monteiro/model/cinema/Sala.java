package com.cine.monteiro.model.cinema;

// Libs
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_SALA")
public class Sala {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String nome;
	
	@NotBlank
	@Length(min = 15, max = 40, message = "[ERROR SALA] - QUANTIDADE DE ASSENTOS INVÁLIDO!")
	@Column(name = "quantidade_assentos")
	private Integer quantidadeAssentos;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "sala")
	private List<Sessao> sessoes = new ArrayList<Sessao>();
	
	// Construtores
	public Sala() { }
	
	public Sala(String nome, Integer quantidadeAssentos) {
		this.nome = nome;
		this.quantidadeAssentos = quantidadeAssentos;
	}
	
	// Métodos
	public void adicionarSessao(Sessao sessao) {
		this.sessoes.add(sessao);
	}
	
	
}
