package com.cine.monteiro.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.domain.model.cinema.Sala;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.services.SalaService;
import com.cine.monteiro.exception.SalaException;

@RestController
@RequestMapping("/sala")
@CrossOrigin(origins = "http://localhost:4200")
public class SalaController {
	
	@Autowired
	private SalaService salaService;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Sala> cadastrar(@Valid @RequestBody Sala sala) throws SalaException {
		salaService.salvar(sala);
		return ResponseEntity.ok(sala);
	}
		
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Sala> deletar(@PathVariable Long id, @AuthenticationPrincipal User user) {
		
		System.out.println(user.toString());
		
		try {
			Sala sala = salaService.deletar(id);
			return ResponseEntity.ok(sala);
		} catch (SalaException e) {
			return ResponseEntity.badRequest().build();
		}
		
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
