package com.cine.monteiro.model.cinema;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_INGRESSO")
public class Ingresso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}