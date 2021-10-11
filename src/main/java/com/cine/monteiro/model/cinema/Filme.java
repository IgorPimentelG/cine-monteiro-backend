package com.cine.monteiro.model.cinema;

// Libs
import lombok.Data;
import javax.persistence.*;

// Pacotes
import com.cine.monteiro.enumeracoes.*;

@Data
@Entity
@Table(name = "TB_FILME")
public class Filme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String titulo;
	
	private String sinopse;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Genero genero;
	
	private Long duracao;
	
	@Enumerated(EnumType.STRING)
	private ClassificacaoEtaria classificacaoEtaria;
	
	@Enumerated(EnumType.STRING)
	private Legenda legenda;
	
	@Enumerated(EnumType.STRING)
	private Projecao projecao;
	
	public String toString() {
		return "\nID: " + this.id +
				"\nTítulo: " + this.titulo +
				"\nSinopse: " + this.sinopse + 
				"\nGênero: " + this.genero + 
				"\nDuração: " + this.duracao + 
				"\nClassificação Etária: " + this.classificacaoEtaria +
				"\nLegenda: " + (this.legenda == Legenda.NONE ? "Não Contém" : this.legenda) + 
				"\nProjeção: " + this.projecao; 
	}
	
}
