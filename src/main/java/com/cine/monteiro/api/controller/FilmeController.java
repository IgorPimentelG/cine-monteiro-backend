package com.cine.monteiro.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.model.cinema.Genero;
import com.cine.monteiro.domain.services.FilmeService;
import com.cine.monteiro.domain.services.GeneroService;
import com.cine.monteiro.exception.FilmeException;

@RestController
@RequestMapping("/filme")
public class FilmeController {
	
	@Autowired
	private FilmeService filmeService;
	
	@Autowired
	private GeneroService generoService;
	
	@PostMapping("/cadastrar")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Filme> cadastrar(@RequestBody @Valid Filme filme) throws FilmeException {
		filmeService.salvar(filme);
		return ResponseEntity.ok(filme);
	}
	
	@PostMapping("/cadastrar/genero")
	@PreAuthorize("hasRole('ADMIN')")
	public Genero cadastrarGenero(@RequestBody @Valid Genero genero) {
		return generoService.salvar(genero);
	}
	
	@PutMapping("/atualizar")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Filme> update(@RequestBody Filme filme) throws FilmeException {
		Filme filmeAtualizado = filmeService.atualizar(filme);
		return ResponseEntity.ok(filmeAtualizado);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Filme> pesquisar(@PathVariable Long id) throws FilmeException {
		Filme filme = filmeService.buscar(id);
		return ResponseEntity.ok(filme);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Filme>> listar() throws FilmeException {
		List<Filme> filmes = filmeService.listar();
		return ResponseEntity.ok(filmes);
	}

}
