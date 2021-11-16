package com.cine.monteiro.utils;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.UserRepository;
import com.cine.monteiro.exception.UserException;

@Component
public class UserUtils {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
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
	
	public void validarTelefone(String telefone) throws ValidationException {
		
		if(telefone.matches("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{5}[- .]?\\d{4}$")) {
			return;
		}
		throw new ValidationException("[ERROR VALIDATION] - TELEFONE NÃO CUMPRE OS REQUISITOS NECESSÁRIOS!");
	}

	public User autenticar(String email, String password) throws UserException {
		
		User user = userRepository.findByEmail(email);

		if(user == null) {
			throw new UserException("E-MAIL NÃO CADASTRADO!");
		} else {
			if(encoder.matches(password, user.getPassword())) {
				return user;
			} else {
				throw new UserException("SENHA INCORRETA!");
			}
		}

	}
}
