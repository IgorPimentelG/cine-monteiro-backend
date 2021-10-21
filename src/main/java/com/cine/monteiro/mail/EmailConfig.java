package com.cine.monteiro.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {
	
	@Autowired
	private JavaMailSender emailSender;
	
	public void enviarEmail(String destinatario, String titulo, String texto) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("noreplay@cinemonteiro.com");
		mensagem.setTo(destinatario);
		mensagem.setSubject(titulo);
		mensagem.setText(texto);
		emailSender.send(mensagem);
	}

}
