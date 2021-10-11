package com.cine.monteiro.model.relatorio;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_SALA_RELATORIO")
public class SalaRelatorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
