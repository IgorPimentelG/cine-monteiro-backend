package com.cine.monteiro.model.cinema;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.cine.monteiro.enumeracoes.*;

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
	
	@NotBlank
	@Length(min = 15)
	private Long duracao;
	
	@NotBlank
	@Enumerated(EnumType.STRING)
	@Column(name = "classificacao_etaria")
	private ClassificacaoEtaria classificacaoEtaria;
	
	@NotBlank
	@Enumerated(EnumType.STRING)
	private Legenda legenda;
	
	@NotBlank
	@Enumerated(EnumType.STRING)
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
