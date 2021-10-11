package com.cine.monteiro.model.relatorio;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_SESSAO_RELATORIO")
public class SessaoRelatorio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
}
