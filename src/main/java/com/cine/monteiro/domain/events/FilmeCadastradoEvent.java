package com.cine.monteiro.domain.events;

import com.cine.monteiro.domain.model.cinema.Filme;

import lombok.Data;

@Data
public class FilmeCadastradoEvent {

	private Filme filme;
	
	public FilmeCadastradoEvent(Filme filme) {
		this.filme = filme;
	}
	
}
