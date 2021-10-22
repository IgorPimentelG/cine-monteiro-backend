package com.cine.monteiro.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import com.cine.monteiro.domain.events.IngressoEmitidoEvent;
import com.cine.monteiro.mail.EmailConfig;

public class EnviarBoletoListener {
	
	@Autowired
	private EmailConfig emailConfig;
	
	@EventListener
	public void enviarBoleto(IngressoEmitidoEvent event) {
		
	}

}
