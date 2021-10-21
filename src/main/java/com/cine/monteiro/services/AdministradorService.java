package com.cine.monteiro.services;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.exception.UserException;
import com.cine.monteiro.model.users.Administrador;
import com.cine.monteiro.repository.AdministradorRepository;
import com.cine.monteiro.utils.UserUtils;

@Service
public class AdministradorService {
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private UserUtils userUtils;
	
	public Administrador salvar(Administrador administrador) throws UserException {

		if(this.listar().size() == 3) { 
			throw new UserException("LIMITE DE ADMINISTRADORES ALCANÇADO");
		}
		
		// Validações
		userUtils.validarEmail(administrador.getEmail());
		userUtils.validarPassword(administrador.getPassword());
		
		if(administradorRepository.findByEmail(administrador.getEmail()) != null) {
			throw new UserException("ADMINISTRADOR JÁ CADASTRADO!");
		}
		
		administrador.setPassword(userUtils.encodePassword(administrador.getPassword()));				// Encriptar Senha
		
		return administradorRepository.save(administrador);
	}
	
	public Administrador deletar(Long id) throws UserException {
		Administrador administrador = administradorRepository.findById(id).get();
		
		validarRetorno(administrador);
		
		administradorRepository.deleteById(id);
		return administrador;
	}
	
	public Administrador update(Administrador administrador) throws UserException  {
		Administrador administradorDesatualizado = administradorRepository.findById(administrador.getId()).get();
		
		validarRetorno(administradorDesatualizado);
		
		BeanUtils.copyProperties(administrador, administradorDesatualizado, "id");

		return administradorRepository.save(administrador);
	}
	
	public Administrador pesquisar(Long id) throws UserException {
		Administrador administrador = administradorRepository.findById(id).get();
	
		validarRetorno(administrador);
		
		return administrador;
	}
	
	public List<Administrador> listar() throws UserException {
		
		List<Administrador> administradores = administradorRepository.findAll();
	
		if(administradores.isEmpty()) {
			throw new UserException("NÃO EXISTE ADMINISTRADORES CADASTRADOS!");
		}
		
		return administradores;
	}
	
	private void validarRetorno(Administrador administrador) throws UserException {
		if(administrador == null) {
			throw new UserException("ADMINISTRADOR NÃO EXISTE!");
		}
	}
	
	public Administrador autenticar(String email, String password) throws UserException {
		return userUtils.autenticarAdmin(email, password);
	}
	
}
