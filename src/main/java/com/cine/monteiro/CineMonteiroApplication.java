package com.cine.monteiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//não executa a classe SecurityAutoConfiguration. Ela é responsável pelo usuário e senha da aplicação
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CineMonteiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CineMonteiroApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//encriptador
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	

}
