package com.cine.monteiro.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cine.monteiro.domain.events.IngressoEmitidoEvent;
import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.repository.SessaoRepository;

@Component
public class ConfigSessaoListener {
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@EventListener
	public void reconfigurarSessao(IngressoEmitidoEvent event) {
		Sessao sessao = event.getIngresso().getSessao();
		
		int quantidadeComprada = event.getIngresso().getQuantidade();
		
		sessao.setQuantidadeVagasDisponiveis(sessao.getQuantidadeVagasDisponiveis() - quantidadeComprada);
		
		for(String assento : event.getIngresso().getAssentosReservados()) {
			sessao.adicionarAssentoReservado(assento);
		}
		
		sessaoRepository.save(sessao);
	}

}
