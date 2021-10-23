package com.cine.monteiro.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import com.cine.monteiro.domain.events.IngressoCanceladoEvent;
import com.cine.monteiro.domain.events.IngressoEmitidoEvent;
import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.model.cinema.Ingresso;
import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.mail.EmailConfig;

public class NotificadorEmailListener {
	
	@Autowired
	private EmailConfig emailConfig;
	
	@EventListener
	public void enviarConfirmacaoCompra(IngressoEmitidoEvent event) {
		
		Ingresso ingresso = event.getIngresso();
		Cliente cliente = event.getIngresso().getCliente();
		Filme filme = ingresso.getSessao().getFilme();
		
		String texto = String.format(
				"Olá, %s \n\nSeu ingresso para o filme %s foi emitido com sucesso!\nSegue seu comprovante em anexo."
				,cliente.getNome(), filme.getTitulo());
		
		emailConfig.enviarEmail(cliente.getEmail(), "CINE MONTEIRO - INGRESSO", texto);
	}
	
	@EventListener
	public void enviarConfirmacaoCancelamento(IngressoCanceladoEvent event) {
		
		Ingresso ingresso = event.getIngresso();
		Cliente cliente = event.getIngresso().getCliente();
		Filme filme = ingresso.getSessao().getFilme();
		
		String texto = String.format(
				"Olá, %s \n\nSeu ingresso para o filme %s foi cancelado com sucesso!"
				,cliente.getNome(), filme.getTitulo());
		
		emailConfig.enviarEmail(cliente.getEmail(), "CINE MONTEIRO - INGRESSO", texto);
	}

}
