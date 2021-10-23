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
