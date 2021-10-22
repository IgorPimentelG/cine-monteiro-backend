package com.cine.monteiro.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.model.relatorio.FilmeRelatorio;
import com.cine.monteiro.domain.repository.FilmeRelatorioRepository;

@Service
public class FilmeRelatorioService {
	
	@Autowired
	private FilmeRelatorioRepository filmeRelatorioRepository;

	public void registrarRelatorio(Filme filme) {	
		FilmeRelatorio filmeRelatorio = new FilmeRelatorio(filme);
		filmeRelatorioRepository.save(filmeRelatorio);
	}
	
	public FilmeRelatorio pesquisar(Long id) {
		return filmeRelatorioRepository.findById(id).get();
	}
	
}
