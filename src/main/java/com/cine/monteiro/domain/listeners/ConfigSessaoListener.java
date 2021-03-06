package com.cine.monteiro.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cine.monteiro.domain.events.IngressoCanceladoEvent;
import com.cine.monteiro.domain.events.IngressoEmitidoEvent;
import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.services.SessaoService;
import com.cine.monteiro.exception.SessaoException;

import lombok.Data;

@Component
@Data
public class ConfigSessaoListener {

	private SessaoService sessaoService;
	
	@Autowired
	public ConfigSessaoListener(SessaoService sessaoService) {
		this.sessaoService = sessaoService;
	}
	
	@EventListener
	public Sessao reconfigurarSessaoIngressoComprado(IngressoEmitidoEvent event) throws SessaoException {
		
		Sessao sessao = sessaoService.buscar(event.getIngresso().getSessao().getId());
		
		int quantidadeComprada = event.getIngresso().getQuantidade();
		
		sessao.setQuantidadeVagasDisponiveis(sessao.getQuantidadeVagasDisponiveis() - quantidadeComprada);
		
		for(String assento : event.getIngresso().getAssentosReservados()) {
			sessao.adicionarAssentoReservado(assento);
		}
		
		return sessaoService.update(sessao);
	}
	
	@EventListener
	public void reconfigurarSessaoIngressoCancelado(IngressoCanceladoEvent event) throws SessaoException {
		
		Sessao sessao = sessaoService.buscar(event.getIngresso().getSessao().getId());
		
		int quantidadeComprada = event.getIngresso().getQuantidade();
		
		sessao.setQuantidadeVagasDisponiveis(sessao.getQuantidadeVagasDisponiveis() + quantidadeComprada);
		
		for(String assento : event.getIngresso().getAssentosReservados()) {
			sessao.removerAssentoReservado(assento);
		}
		
		sessaoService.update(sessao);
	}

}
