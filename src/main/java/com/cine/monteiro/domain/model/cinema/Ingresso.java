package com.cine.monteiro.domain.model.cinema;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.cine.monteiro.domain.model.user.Cliente;

import lombok.Data;



@Data
@Entity
@Table(name = "TB_INGRESSO")
public class Ingresso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_fk")
	private Cliente cliente;
	
	@NotBlank
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sessao_fk")
	private Sessao sessao;

	@NotBlank
	private Integer quantidade;
	
	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "TB_ASSENTOS_RESERVADOS_INGRESSO")
	@Column(name = "assentos_reservados_ingresso")
	private Set<String> assentosReservados;
	
	public void adicionarAssento(String assento) {
		this.assentosReservados.add(assento);
	}
	
	public String toString() {
		return "\nID: "+ this.id + 
				"\nCliente: " + this.cliente.toString() + 
				"\nSessão: " + this.sessao.toString() + 
				"\nQuantidade: " + this.quantidade + 
				"\nValor Unitário: R$ " + this.valorUnitario + 
				"\nValor total: R$ " + this.valorTotal + 
				"\nAssentos Reservados: " + StringUtils.join(this.assentosReservados,", ");
	}
}
