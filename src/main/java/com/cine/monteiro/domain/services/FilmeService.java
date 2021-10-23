package com.cine.monteiro.domain.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.repository.FilmeRepository;
import com.cine.monteiro.exception.FilmeException;

@Service
public class FilmeService {

	@Autowired
	private FilmeRepository filmeRepository;
	
	public Filme salvar(Filme filme) throws FilmeException {
		
		if(filme.getDuracao() < 15) {
			throw new FilmeException("DURAÇÃO DO FILME MUITO CURTA!");
		}
		
		return filmeRepository.save(filme);
	}
	
	public Filme atualizar(Filme filme) throws FilmeException {
		Filme filmeDesatualizado = filmeRepository.findById(filme.getId()).get();

		validarRetorno(filmeDesatualizado);
		
		BeanUtils.copyProperties(filme, filmeDesatualizado, "id");
		return filmeRepository.save(filme);
	}
	
	public List<Filme> listar() throws FilmeException {
		List<Filme> filmes = filmeRepository.findAll();
		
		if(filmes.isEmpty()) {
			throw new FilmeException("NÃO EXISTE FILMES CADASTRADOS!");
		}
		
		return filmes;
	}
	
	public Filme buscar(Long id) throws FilmeException {
		Filme filme = filmeRepository.findById(id).get();
		validarRetorno(filme);
		return filme;
	}
	
	private void validarRetorno(Filme filme) throws FilmeException {
		if(filme == null) {
			throw new FilmeException("FILME NÃO CADASTRADO!");
		}
	}
	
	
}
