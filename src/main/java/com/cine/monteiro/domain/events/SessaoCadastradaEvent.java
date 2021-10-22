package com.cine.monteiro.domain.events;

import com.cine.monteiro.domain.model.cinema.Sessao;

import lombok.Data;

@Data
public class SessaoCadastradaEvent {

	private Sessao sessao;
	
	public SessaoCadastradaEvent(Sessao sessao) {
		this.sessao = sessao;
	}
	
}
