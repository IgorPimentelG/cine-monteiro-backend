package com.cine.monteiro.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.model.cinema.Filme;
import com.cine.monteiro.repository.FilmeRepository;

@Service
public class FilmeService {

	@Autowired
	private FilmeRepository filmeRepository;
	
	@Autowired
	private FilmeRelatorioService filmeRelatorioService;
	
	public Filme salvar(Filme filme) {
		filmeRepository.save(filme);
		filmeRelatorioService.registrarRelatorio(filme);
		return filme;
	}
	
	public Filme atualizar(Filme filme) {
		Filme filmeDesatualizado = filmeRepository.findById(filme.getId()).get();
		BeanUtils.copyProperties(filme, filmeDesatualizado, "id");
		filmeRepository.save(filme);
		return filme;
	}
	
	public List<Filme> listar() {
		return filmeRepository.findAll();
	}
	
	public Filme buscar(Long id) {
		return filmeRepository.findById(id).get();
	}
	
	
}
