package com.cine.monteiro.domain.events;

import com.cine.monteiro.domain.model.cinema.Ingresso;

import lombok.Data;

@Data
public class IngressoCanceladoEvent {
	
	private Ingresso ingresso;
	
	public IngressoCanceladoEvent(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
}
