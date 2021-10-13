package com.cine.monteiro.model.relatorio;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_RELATORIO")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Relatorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Long id;
	
	@Column(columnDefinition = "DATE")
	protected LocalDate dataRegistro;
	
	@Column(name = "lucro_total")
	protected BigDecimal lucroTotal;
	
	@Column(name = "quantidade_ingressos_vendidos")
	protected Integer quantidadeIngressosVendidos;
	
	public Relatorio() {
		this.dataRegistro = LocalDate.now();
		this.lucroTotal = new BigDecimal(0);
		this.quantidadeIngressosVendidos = 0;
	}

}
