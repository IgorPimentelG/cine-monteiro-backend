package com.cine.monteiro.model.cinema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_SESSAO")
public class Sessao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "filme_fk")
	private Filme filme;
	
	@ManyToOne
	@JoinColumn(name = "sala_fk")
	private Sala sala;
	
	@Column(name = "preco_ingresso")
	private BigDecimal precoIngresso;
	
	@Column(name = "hora_inicio_exibicao")
	private LocalTime horaDeInicioExibicao;
	
	@Column(name = "hora_termino_exibicao")
	private LocalTime horaDeTerminoExibicao;
	
	@Column(name = "inicio_periodo_exibicao")
	private LocalDate inicioPeriodoExibicao;
	
	@Column(name = "termino_periodo_exibicao")
	private LocalDate terminoPeriodoExibicao;
	
	@Column(name = "quantidade_vagas_disponiveis")
	private Integer quantidadeVagasDisponiveis;
	
	private boolean isAtiva;
	
	private boolean isInterrompida;
	
	private boolean isInterrompidaPorUmDia;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "TB_ASSENTOS_RESERVADOS_SESSAO")
	@Column(name = "assentos_reservados")
	private List<String> assentosReservados;
	
	public Sessao() {
		
		// Verificar se sessão está ativa
		if(this.inicioPeriodoExibicao.isEqual(LocalDate.now())) {
			this.isAtiva = true;
		} else {
			this.isAtiva = false;
		}
		
		this.isInterrompida = false;
		this.isInterrompidaPorUmDia = false;
		
		this.quantidadeVagasDisponiveis = sala.getQuantidadeAssentos();
		
		this.assentosReservados = new ArrayList<String>();
	}

}