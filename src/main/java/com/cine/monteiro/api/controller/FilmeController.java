package com.cine.monteiro.api.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.model.cinema.Genero;
import com.cine.monteiro.domain.services.FilmeService;
import com.cine.monteiro.domain.services.GeneroService;
import com.cine.monteiro.exception.FilmeException;

@RestController
@RequestMapping("/filme")
@CrossOrigin(origins = "http://localhost:4200")
public class FilmeController {
	
	@Autowired
	private FilmeService filmeService;
	
	@Autowired
	private GeneroService generoService;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Filme> cadastrar(@RequestBody @Valid Filme filme) throws URISyntaxException {
		
		try {

			filmeService.salvar(filme);
			
			return ResponseEntity.created(new URI("http://localhost:8080/filme/cadastrar")).build();
		} catch (FilmeException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/cadastrar/genero")
	public Genero cadastrarGenero(@RequestBody @Valid Genero genero) {
		return generoService.salvar(genero);
	}
	
	@GetMapping("/genero/listar")
	public List<Genero> listarGeneros() {
		return generoService.listar();
	}
	
	@PutMapping("/atualizar")
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
