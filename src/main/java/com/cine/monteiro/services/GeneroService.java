package com.cine.monteiro.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.model.cinema.Genero;
import com.cine.monteiro.repository.GeneroRepository;

@Service
public class GeneroService {
	
	@Autowired
	private GeneroRepository generoRepository;
	
	public void salvar(Genero genero) {
		generoRepository.save(genero);
	}
	
	public void deletar(Long id) {
		generoRepository.deleteById(id);
	}
	
	public void atualizar(Genero genero) {
		Genero generoDesatualizado = generoRepository.findById(genero.getId()).get();
		BeanUtils.copyProperties(genero, generoDesatualizado, "id");
		generoRepository.save(genero);
	}
	
	public List<Genero> listar() {
		return generoRepository.findAll();
	}
	
	public Genero buscar(Long id) {
		return generoRepository.findById(id).get();
	}

}
