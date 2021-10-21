package com.cine.monteiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.exception.SalaException;
import com.cine.monteiro.model.cinema.Sala;
import com.cine.monteiro.services.SalaService;

@RestController
@RequestMapping("/sala")
public class SalaController {
	
	@Autowired
	private SalaService salaService;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Sala> cadastrar(@RequestBody Sala sala) throws SalaException {
		salaService.salvar(sala);
		return ResponseEntity.ok(sala);
	}
		
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Sala> deletar(@PathVariable Long id) throws SalaException {
		Sala sala = salaService.deletar(id);
		return ResponseEntity.ok(sala);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Sala>> listar() throws SalaException {
		List<Sala> salas = salaService.listar();
		return ResponseEntity.ok(salas);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Sala> pesquisar(@PathVariable Long id) throws SalaException {
		Sala sala = salaService.pesquisar(id);
		return ResponseEntity.ok(sala);
	}

}
