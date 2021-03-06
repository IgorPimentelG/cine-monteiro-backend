package com.cine.monteiro.domain.model.cinema;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.cine.monteiro.domain.enums.*;

@Data
@Entity
@Table(name = "TB_FILME")
public class Filme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String titulo;
	
	private String sinopse;
	
	@Lob
	private String capa;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Genero genero;
	
	private Long duracao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "classificacao_etaria", nullable = false)
	private ClassificacaoEtaria classificacaoEtaria;
	
	@Enumerated(EnumType.STRING)
	private Legenda legenda;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Projecao projecao;
	
	// Construtores
	public Filme() {}

	public Filme(String titulo, String sinopse, Genero genero, Long duracao, ClassificacaoEtaria classificacaoEtaria, 
			Legenda legenda, Projecao projecao) {
		this.titulo = titulo;
		this.sinopse = sinopse;
		this.genero = genero;
		this.duracao = duracao;
		this.classificacaoEtaria = classificacaoEtaria;
		this.legenda = legenda;
		this.projecao = projecao;
	}
	
	public String toString() {
		return "\nID: " + this.id +
				"\nTítulo: " + this.titulo +
				"\nSinopse: " + this.sinopse + 
				"\nGênero: " + this.genero.toString() + 
				"\nDuração: " + this.duracao + 
				"\nClassificação Etária: " + this.classificacaoEtaria +
				"\nLegenda: " + (this.legenda == Legenda.NONE ? "Não Contém" : this.legenda) + 
				"\nProjeção: " + this.projecao; 
	}
	
}
