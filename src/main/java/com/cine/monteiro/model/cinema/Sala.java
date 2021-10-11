package com.cine.monteiro.model.cinema;

// Libs
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_SALA")
public class Sala {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nome;
	
	@Column(name = "quantidade_assentos")
	private Integer quantidadeAssentos;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "sala_fk")
	private List<Sessao> sessoes = new ArrayList<Sessao>();
	
}
