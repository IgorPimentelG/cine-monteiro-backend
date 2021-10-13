package com.cine.monteiro.model.cinema;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_GENERO")
public class Genero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String genero;
	
	@Column(nullable = true)
	private String descricao;

}
