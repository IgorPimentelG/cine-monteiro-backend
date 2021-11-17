package com.cine.monteiro.domain.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.UserRepository;
import com.cine.monteiro.exception.UserException;
import com.cine.monteiro.utils.UserUtils;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserUtils userUtils;
	
	public User salvar(User user) throws UserException {
		// Validações
		userUtils.validarEmail(user.getEmail());
		userUtils.validarPassword(user.getPassword());
		
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserException("E-MAIL JÁ CADASTRADO!");
		}
		
		user.setPassword(userUtils.encodePassword(user.getPassword()));				// Encriptar Senha
		
		return userRepository.save(user);
	}
	
	public User deletar(Long id) throws UserException {
		User user = pesquisar(id);
		userRepository.delete(user);
		return user;
	}
	
	public User atualizar(User user) throws UserException {
		User userDestaulizado = pesquisar(user.getId());
		BeanUtils.copyProperties(user, userDestaulizado, "id");
		return userRepository.save(user);
	}
	
	public User pesquisar(Long id) throws UserException {
		User user = userRepository.findById(id).get();
		validarRetorno(user);
		return user;
	}
	
	public List<User> listar() throws UserException {
		List<User> users = userRepository.findAll();
		
		if(users.isEmpty()) {
			throw new UserException("NÃO EXISTE USUÁRIOS CADASTRADOS!");
		}
		
		return users;
	}
	
	public User autenticar(String email, String password) throws UserException {
		return userUtils.autenticar(email, password);
	}

	private void validarRetorno(User user) throws UserException {
		if(user == null) {
			throw new UserException("USUÁRIO NÃO EXISTE!");
		}
	}
	

}