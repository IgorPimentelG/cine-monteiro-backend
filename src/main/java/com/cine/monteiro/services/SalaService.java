package com.cine.monteiro.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.model.cinema.Sala;
import com.cine.monteiro.repository.SalaRepository;

@Service
public class SalaService {
	
	@Autowired
	private SalaRepository salaRepository;
	
	public Sala salvar(Sala sala) {
		salaRepository.save(sala);
		return sala;
	}
	
	public Sala update(Sala sala) {
		Sala salaDesatualizada = salaRepository.findById(sala.getId()).get();
		BeanUtils.copyProperties(sala, salaDesatualizada, "id");
		salaRepository.save(sala);
		return sala;
	}
	
	public void deletar(Long id) {
		salaRepository.deleteById(id);
	}
	
	public List<Sala> listar() {
		return salaRepository.findAll();
	}
	
	public Sala pesquisar(Long id) {
		return salaRepository.findById(id).get();
	}
	
}
