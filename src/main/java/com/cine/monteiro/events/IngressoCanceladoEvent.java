package com.cine.monteiro.events;

import com.cine.monteiro.model.cinema.Ingresso;

import lombok.Data;

@Data
public class IngressoCanceladoEvent {
	
	private Ingresso ingresso;
	
	public IngressoCanceladoEvent(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
}
