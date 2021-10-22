package com.cine.monteiro.domain.events;

import com.cine.monteiro.domain.model.cinema.Ingresso;

import lombok.Data;

@Data
public class IngressoEmitidoEvent {

	private Ingresso ingresso;
	
	public IngressoEmitidoEvent(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
	
}
