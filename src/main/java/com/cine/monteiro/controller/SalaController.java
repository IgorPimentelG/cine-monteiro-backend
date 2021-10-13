package com.cine.monteiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.model.cinema.Sala;
import com.cine.monteiro.services.SalaService;

@RestController
@RequestMapping("/sala")
public class SalaController {
	
	@Autowired
	private SalaService salaService;
	
	@PostMapping("/cadastrar")
	public Sala cadastrar(@RequestBody Sala sala) {
		return salaService.salvar(sala);
	}
	
	@PutMapping("/atualizar")
	public Sala update(@RequestBody Sala sala) {
		return salaService.update(sala);
	}
	
	@DeleteMapping("/deletar/{id}")
	public void deletar(@PathVariable Long id) {
		salaService.deletar(id);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Sala>> listar() {
		List<Sala> salas = salaService.listar();
		return salas.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(salas);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Sala> pesquisar(@PathVariable Long id) {
		Sala sala = salaService.pesquisar(id);
		return sala == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(sala);
	}

}
