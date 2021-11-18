package com.cine.monteiro.mail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {
	
	@Autowired
	private JavaMailSender emailSender;
	
	public boolean enviarEmail(@NotBlank @Email String destinatario, @NotBlank String titulo, @NotBlank String texto) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("noreplay@cinemonteiro.com");
		mensagem.setTo(destinatario);
		mensagem.setSubject(titulo);
		mensagem.setText(texto);
		
		try {
			emailSender.send(mensagem);
			return true;
		} catch(MailException error) {
			return false;
		}
	}

}
