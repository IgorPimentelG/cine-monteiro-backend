package com.cine.monteiro.domain.model.cinema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

@Data
@Entity
@Table(name = "TB_SESSAO")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
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
	private Set<String> assentosReservados = new HashSet<String>();
	
	// Construtores
	public Sessao() { }
	
	public Sessao(Filme filme, Sala sala, BigDecimal precoIngresso, LocalTime horaDeInicioExibicao,
			LocalTime horaDeTerminoExibicao, LocalDate inicioPeriodoExibicao,
			LocalDate terminoPeriodoExibicao, Integer quantidadeVagasDisponiveis, boolean isAtiva) {
		this.filme = filme;
		this.sala = sala;
		this.precoIngresso = precoIngresso;
		this.horaDeInicioExibicao = horaDeInicioExibicao;
		this.horaDeTerminoExibicao = horaDeTerminoExibicao;
		this.inicioPeriodoExibicao = inicioPeriodoExibicao;
		this.terminoPeriodoExibicao = terminoPeriodoExibicao;
		this.quantidadeVagasDisponiveis = quantidadeVagasDisponiveis;
		this.isAtiva = isAtiva;
	}
	
	public void adicionarAssentoReservado(String assento) {
		assentosReservados.add(assento);
	}
	
	public void removerAssentoReservado(String assento) {
		assentosReservados.remove(assento);
	}
	
	public String toString() {
		return "\nID: " + this.id + 
				"\nPre??o do Ingresso: R$ " + this.precoIngresso + 
				"\nHora de in??cio de exibi????o: " + this.horaDeInicioExibicao + 
				"\nHora do t??rmino de exibi????o: " + this.horaDeTerminoExibicao +
				"\nData de in??cio de exibi????o: " + this.inicioPeriodoExibicao + 
				"\nData do t??rmino de exibi????o: " + this.terminoPeriodoExibicao + 
				"\nAtiva: " + (this.isAtiva ? "SIM" : "N??O");
	}

	
	
	
}
