package com.cine.monteiro.domain.model.relatorio;


import javax.persistence.*;

import com.cine.monteiro.domain.model.cinema.Filme;

import lombok.*;

@Data
@Entity
@Table(name = "TB_FILME_RELATORIO")
@EqualsAndHashCode(callSuper = false)
public class FilmeRelatorio extends Relatorio {
	
	@OneToOne(fetch = FetchType.EAGER)
	@Setter(value = AccessLevel.NONE)
	private Filme filme;
	
	@Column(name = "total_exibicoes")
	private Integer totalExibicoes;
	
	@Column(name = "total_sessoes")
	private Integer totalDeSessoes;

	// Construtores
	public FilmeRelatorio(Filme filme) {
		super();
		this.filme = filme;
		this.totalExibicoes = 0;
		this.totalDeSessoes = 0;
	}
	
	public String toString() {
		return "\nID: " + super.id + 
				"\nLucro Total: " + super.lucroTotal + 
				"\nQuantidade Ingressos Vendidos: " + super.quantidadeIngressosVendidos +
				"\nTotal de Exibições: " + this.totalExibicoes + 
				"\nTotal de Sessões: " + this.totalDeSessoes;
	}
}
