package com.cine.monteiro.events;

import com.cine.monteiro.model.cinema.Ingresso;

import lombok.Data;

@Data
public class IngressoEmitidoEvent {

	private Ingresso ingresso;
	
	public IngressoEmitidoEvent(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
	
}
