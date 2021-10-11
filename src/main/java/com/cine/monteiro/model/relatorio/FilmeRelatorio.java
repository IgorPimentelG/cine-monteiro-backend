package com.cine.monteiro.model.relatorio;

import java.math.BigDecimal;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

// Pacotes
import com.cine.monteiro.model.cinema.Filme;

@Data
@Entity
@Table(name = "TB_FILME_RELATORIO")
public class FilmeRelatorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@Setter(value = AccessLevel.NONE)
	private Filme filme;
	
	@Column(name = "lucro_total")
	private BigDecimal lucroTotal;
	
	@Column(name = "quantidade_ingressos_vendidos")
	private Integer quantidadeIngressosVendidos;
	
	@Column(name = "total_exibicoes")
	private Integer totalExibicoes;
	
	@Column(name = "total_sessoes")
	private Integer totalDeSessoes;

	// Construtores
	public FilmeRelatorio() {}
	
	public FilmeRelatorio(Filme filme) {
		this.filme = filme;
	}
	
	public String toString() {
		return "\nID: " + this.id + 
				"\nLucro Total: " + this.lucroTotal + 
				"\nQuantidade Ingressos Vendidos: " + this.quantidadeIngressosVendidos +
				"\nTotal de Exibições: " + this.totalExibicoes + 
				"\nTotal de Sessões: " + this.totalDeSessoes;
	}
}
