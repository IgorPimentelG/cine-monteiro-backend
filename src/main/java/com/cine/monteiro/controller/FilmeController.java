package com.cine.monteiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.model.cinema.Filme;
import com.cine.monteiro.model.cinema.Genero;
import com.cine.monteiro.services.FilmeService;
import com.cine.monteiro.services.GeneroService;

@RestController
@RequestMapping("/filme")
public class FilmeController {
	
	@Autowired
	private FilmeService filmeService;
	
	@Autowired
	private GeneroService generoService;
	
	@PostMapping("/cadastrar")
	public Filme cadastrar(@RequestBody Filme filme) {
		Filme filmeCadastrado = filmeService.salvar(filme);
		return filmeCadastrado;
	}
	
	@PostMapping("/cadastrar/genero")
	public Genero cadastrarGenero(@RequestBody Genero genero) {
		return generoService.salvar(genero);
	}
	
	@PutMapping("/atualizar")
	public Filme update(@RequestBody Filme filme) {
		Filme filmeAtualizado = filmeService.atualizar(filme);
		return filmeAtualizado;
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Filme> pesquisar(@PathVariable Long id) {
		Filme filme = filmeService.buscar(id);
		return filme == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(filme);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Filme>> listar() {
		List<Filme> filmes = filmeService.listar();
		return filmes.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(filmes);
	}

}
