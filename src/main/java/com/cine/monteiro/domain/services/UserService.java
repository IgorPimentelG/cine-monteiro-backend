package com.cine.monteiro.domain.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.model.user.Profile;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.ProfileRepository;
import com.cine.monteiro.domain.repository.UserRepository;
import com.cine.monteiro.exception.UserException;
import com.cine.monteiro.mail.EmailConfig;
import com.cine.monteiro.utils.UserUtils;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	@Autowired private ProfileRepository profileRepository;
	@Autowired private UserUtils userUtils;
	@Autowired private EmailConfig emailConfig;
	
	public User salvar(User user) throws UserException {
		// Validações
		userUtils.validarEmail(user.getEmail());
		userUtils.validarPassword(user.getPassword());
		
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserException("E-MAIL JÁ CADASTRADO!");
		}
		
		Profile profile = new Profile();
		
		if(userRepository.findAll().size() == 0) {
			profile.setProfile("ADMIN");
		} else {
			profile.setProfile("CLIENT");
		}
		
		if(profileRepository.findByProfile(profile.getProfile()) != null) {
			profile = profileRepository.findByProfile(profile.getProfile());
		} else {
			profileRepository.save(profile);
		}
		
		List<Profile> profiles = new ArrayList<Profile>();
		profiles.add(profile);
		user.setProfiles(profiles);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			
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
	
	public void recuperarPassword(String email) throws UserException {
		
		User user = userRepository.findByEmail(email);
		
		validarRetorno(user);
		
		String passwordGerada = userUtils.gerarNovaSenha();
		
		String mensagem = String.format("Olá, %s \n\nUtilize a senha abaixo para acessar sua conta no Cine Monteiro. \nSua nova senha: %s",
				user.getNome(), passwordGerada);
		
		boolean emailEnviado = emailConfig.enviarEmail(user.getEmail(), "CINE MONTEIRO - RECUPERAÇÃO DE CONTA", mensagem);
		
		if(emailEnviado) {
			user.setPassword(passwordGerada);
			userRepository.save(user);
		} else {
			throw new UserException("NÃO FOI POSSÍVEL RECUPERAR SUA CONTA. TENTE NOVAMENTE MAIS TARDE!");
		}
		
	}
	
	public String perfilUser(String email) throws UserException {
		User user = userRepository.findByEmail(email);
		return user.getProfiles().get(0).getProfile();
	}
	
	private void validarRetorno(User user) throws UserException {
		if(user == null) {
			throw new UserException("USUÁRIO NÃO EXISTE!");
		}
	}
	

}
