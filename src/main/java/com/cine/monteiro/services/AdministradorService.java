package com.cine.monteiro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.model.users.Administrador;
import com.cine.monteiro.repository.AdministradorRepository;

@Service
public class AdministradorService {
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	public void save(Administrador administrador) {
		
	}
	
	public void deletar(String CPF) {
		
	}
	
	public void update(Administrador administrador) {
		
	}
	
	public void pesquisar(String CPF) {
		
	}

}
