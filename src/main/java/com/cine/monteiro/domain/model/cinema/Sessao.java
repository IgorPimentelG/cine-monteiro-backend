package com.cine.monteiro.domain.model.cinema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

@Data
@Entity
@Table(name = "TB_SESSAO")
public class Sessao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "filme_fk")
	private Filme filme;
	
	@ManyToOne
	@JoinColumn(name = "sala_fk")
	private Sala sala;
	
	@Column(name = "preco_ingresso", nullable = false)
	private BigDecimal precoIngresso;
	
	@Column(name = "hora_inicio_exibicao", columnDefinition = "TIME", nullable = false)
	private LocalTime horaDeInicioExibicao;
	
	@Column(name = "hora_termino_exibicao", columnDefinition = "TIME")
	private LocalTime horaDeTerminoExibicao;
	
	@Future
	@Column(name = "inicio_periodo_exibicao", columnDefinition = "DATE")
	private LocalDate inicioPeriodoExibicao;
	
	@Future
	@Column(name = "termino_periodo_exibicao", columnDefinition = "DATE")
	private LocalDate terminoPeriodoExibicao;
	
	@Column(name = "data_registro_interrupcao", columnDefinition = "TIMESTAMP")
	private LocalDateTime dataResgistroInterrupcao;
	
	@Column(name = "quantidade_vagas_disponiveis")
	private Integer quantidadeVagasDisponiveis;
	
	private boolean isAtiva;
	
	private boolean isInterrompida;
	
	private boolean isInterrompidaPorUmDia;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "TB_ASSENTOS_RESERVADOS_SESSAO")
	@Column(name = "assentos_reservados_sessao")
	private Set<String> assentosReservados;
	
	public Sessao() {
		this.isAtiva = true;
	}
	
	public void adicionarAssentoReservado(String assento) {
		assentosReservados.add(assento);
	}
	
	public String toString() {
		return "\nID: " + this.id + 
				"\nPreço do Ingresso: R$ " + this.precoIngresso + 
				"\nHora de início de exibição: " + this.horaDeInicioExibicao + 
				"\nHora do término de exibição: " + this.horaDeTerminoExibicao +
				"\nData de início de exibição: " + this.inicioPeriodoExibicao + 
				"\nData do término de exibição: " + this.terminoPeriodoExibicao + 
				"\nAtiva: " + (this.isAtiva ? "SIM" : "NÃO");
	}
	
	
}
