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
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "filme_fk")
	private Filme filme;
	
	@ManyToOne
	@JoinColumn(name = "sala_fk")
	private Sala sala;
	
	@NotBlank
	@Column(name = "preco_ingresso")
	private BigDecimal precoIngresso;
	
	@NotBlank
	@Column(name = "hora_inicio_exibicao", columnDefinition = "TIME")
	private LocalTime horaDeInicioExibicao;
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "hora_termino_exibicao", columnDefinition = "TIME")
	private LocalTime horaDeTerminoExibicao;
	
	@Future
	@NotBlank
	@Column(name = "inicio_periodo_exibicao", columnDefinition = "DATE")
	private LocalDate inicioPeriodoExibicao;
	
	@NotBlank
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
	
	
}
