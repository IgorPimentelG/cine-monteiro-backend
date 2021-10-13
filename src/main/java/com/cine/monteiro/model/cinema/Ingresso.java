package com.cine.monteiro.model.cinema;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.*;

import com.cine.monteiro.model.users.Cliente;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_INGRESSO")
public class Ingresso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_fk")
	private Cliente cliente;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sessao_fk")
	private Sessao sessao;

	private Integer quantidade;
	
	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "TB_ASSENTOS_RESERVADOS_INGRESSO")
	@Column(name = "assentos_reservados_ingresso")
	private Set<String> assentosReservados;
}
