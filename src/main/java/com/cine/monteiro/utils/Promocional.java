package com.cine.monteiro.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.mail.EmailConfig;

@Component
public class Promocional {
	
	private EmailConfig emailConfig;
	
	@Autowired
	public Promocional(EmailConfig emailConfig) {
		this.emailConfig = emailConfig;
	}
	
	public String gerarCupom(User cliente) {
		String cupom = "";
		enviarCupom(cupom, cliente);
		return cupom;
	}
	
	public Integer desconto(String cupom) {
		if(validarCupom(cupom)) {
			return 0;
		}
		return 0;
	}
	
	public boolean validarCupom(String cupom) {
		return true;
	}
	
	private void enviarCupom(String cupom, User cliente) {
		
		String texto = String.format(
				"Olá, %s \n\nUtilize o cupom abaixo na sua próxima compra.\nCupom Promocional: %s"
				,cliente.getNome(), cupom);
		
		emailConfig.enviarEmail(cliente.getEmail(), "CINE MONTEIRO - PROMOCIONAL", texto);
	}

}
