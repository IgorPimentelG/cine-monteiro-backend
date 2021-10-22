package com.cine.monteiro.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.domain.model.cinema.Ingresso;
import com.cine.monteiro.domain.services.IngressoService;
import com.cine.monteiro.exception.IngressoException;

@RestController
@RequestMapping("/ingresso")
public class IngressoController {
	
	@Autowired
	private IngressoService ingressoService;
	
	@PostMapping("/comprar")
	public ResponseEntity<Ingresso> comprar(@RequestBody @Valid Ingresso ingresso) throws IngressoException {
		ingressoService.registrarCompra(ingresso);
		return ResponseEntity.ok(ingresso); 
	}
	
	@DeleteMapping("/cancelar/{id}")
	public ResponseEntity<Ingresso> cancelar(@PathVariable Long id) throws IngressoException {
		Ingresso ingresso = ingressoService.cancelarCompra(id);
		return ResponseEntity.ok(ingresso);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Ingresso> pesquisar(@PathVariable Long id) throws IngressoException {
		Ingresso ingresso = ingressoService.buscar(id);
		return ResponseEntity.ok(ingresso);
	}

}
