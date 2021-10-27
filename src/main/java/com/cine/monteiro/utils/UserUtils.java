package com.cine.monteiro.utils;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cine.monteiro.domain.model.user.Administrador;
import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.domain.repository.AdministradorRepository;
import com.cine.monteiro.domain.repository.ClienteRepository;
import com.cine.monteiro.exception.UserException;

@Component
public class UserUtils {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	public String encodePassword(String password) {
		return encoder.encode(password);
	}
	
	public void validarPassword(String password) throws ValidationException {
		
		if(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
			return;
		}
	
		throw new ValidationException("[ERROR VALIDATION] - PASSWORD NÃO CUMPRE OS REQUISITOS NECESSÁRIOS!");
	}
	
	public void validarEmail(String email) throws ValidationException {
		
		if(email.length() > 0) {
			if(email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
				return;
			}
		}
		throw new ValidationException("[ERROR VALIDATION] - E-MAIL NÃO CUMPRE OS REQUISITOS NECESSÁRIOS!");
	}

	public Cliente autenticarCliente(String email, String password) throws UserException {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new UserException("E-MAIL NÃO CADASTRADO!");
		}
		
		boolean isValid = encoder.matches(password, cliente.getPassword());
		
		if(isValid) {
			return cliente;
		} else {
			throw new UserException("SENHA INCORRETA!");
		}
	}
	
	public Administrador autenticarAdmin(String email, String password) throws UserException {
		
		Administrador administrador = administradorRepository.findByEmail(email);
		
		if(administrador == null) {
			throw new UserException("E-MAIL NÃO CADASTRADO!");
		}
		
		boolean isValid = encoder.matches(password, administrador.getPassword());
		
		if(isValid) {
			return administrador;
		} else {
			throw new UserException("SENHA INCORRETA!");
		}
	}
}
