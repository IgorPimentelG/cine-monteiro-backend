package com.cine.monteiro.domain.model.relatorio;

import java.time.LocalDate;

import javax.persistence.*;

import com.cine.monteiro.domain.model.cinema.Sessao;

import lombok.*;

@Data
@Entity
@Table(name = "TB_SESSAO_RELATORIO")
@EqualsAndHashCode(callSuper = false)
public class SessaoRelatorio extends Relatorio {
	
	@OneToOne(fetch = FetchType.EAGER)
	@Setter(value = AccessLevel.NONE)
	private Sessao sessao;
	
	@Column(name = "quantidade_asentos_vagos")
	private Integer quantidadeAssentosVagos;
	
	public SessaoRelatorio(Sessao sessao) {
		super();
		this.sessao = sessao;
		this.quantidadeAssentosVagos = 0;
	}
	
	public String toString() {
		return "\nID: " + super.id + 
				"\nLucro Total: " + super.lucroTotal + 
				"\nQuantidade Ingressos Vendidos: " + super.quantidadeIngressosVendidos +
				"\nTotal de assentos vagos: " + this.quantidadeAssentosVagos;
	}
	
}
