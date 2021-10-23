package com.cine.monteiro.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.model.cinema.Sala;
import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.repository.SalaRepository;
import com.cine.monteiro.exception.SalaException;

@Service
public class SalaService {
	
	@Autowired
	private SalaRepository salaRepository;
	
	public Sala salvar(Sala sala) throws SalaException {
		
		if(!(sala.getQuantidadeAssentos() >= 15 && sala.getQuantidadeAssentos() <= 40)) {
			throw new SalaException("SALA NÃO CONTÊM A QUANTIDADE NECESSÁRIA PARA CADASTRA");
		}
		
		return salaRepository.save(sala);
	}
	
	public Sala deletar(Long id) throws SalaException {
		
		Sala sala = pesquisar(id);
		
		validarRetorno(sala);
		
		for(Sessao sessao : sala.getSessoes()) {
			if(sessao.isAtiva()) {
				throw new SalaException("A SALA CONTÊM SESSÕES ATIVAS!");
			}
		}
		salaRepository.delete(sala);
		return sala;
	
	}
	
	public List<Sala> listar() throws SalaException {
		List<Sala> salas = salaRepository.findAll();
		
		if(salas.isEmpty()) {
			throw new SalaException("NÃO EXISTE SALAS CADASTRADAS!");
		}
		
		return salas;
	}
	
	public Sala pesquisar(Long id) throws SalaException {
		Sala sala = salaRepository.findById(id).get();
		validarRetorno(sala);
		return sala;
	}
	
	private void validarRetorno(Sala sala) throws SalaException {
		if(sala == null) {
			throw new SalaException("SALA NÃO CADASTRADA!");
		}
	}
	
}
