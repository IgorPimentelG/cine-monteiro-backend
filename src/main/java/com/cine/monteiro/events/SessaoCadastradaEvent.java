package com.cine.monteiro.events;

import com.cine.monteiro.model.cinema.Sessao;

import lombok.Data;

@Data
public class SessaoCadastradaEvent {

	private Sessao sessao;
	
	public SessaoCadastradaEvent(Sessao sessao) {
		this.sessao = sessao;
	}
	
}
