package com.cine.monteiro.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cine.monteiro.domain.events.FilmeCadastradoEvent;
import com.cine.monteiro.domain.events.SessaoCadastradaEvent;
import com.cine.monteiro.domain.services.FilmeRelatorioService;
import com.cine.monteiro.domain.services.SessaoRelatorioService;

@Component
public class RelatorioListener {
	
	@Autowired
	private SessaoRelatorioService sessaoRelatorioService;
	
	@Autowired
	private FilmeRelatorioService filmeRelatorioService;
	
	@EventListener
	public void registrarRelatorioSessao(SessaoCadastradaEvent event) {
		sessaoRelatorioService.registrarRelatorio(event.getSessao());
	}
	
	@EventListener
	public void registrarRelatorioFilme(FilmeCadastradoEvent event) {
		filmeRelatorioService.registrarRelatorio(event.getFilme());
	}

}
