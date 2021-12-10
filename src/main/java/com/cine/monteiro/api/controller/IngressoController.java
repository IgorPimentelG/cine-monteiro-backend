package com.cine.monteiro.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.domain.model.cinema.Ingresso;
import com.cine.monteiro.domain.services.IngressoService;
import com.cine.monteiro.exception.FilmeException;
import com.cine.monteiro.exception.IngressoException;
import com.cine.monteiro.exception.SessaoException;
import com.cine.monteiro.exception.UserException;

@RestController
@RequestMapping("/ingresso")
@CrossOrigin(origins = "http://localhost:4200")
public class IngressoController {
	
	@Autowired
	private IngressoService ingressoService;
	
	@PostMapping("/comprar")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<Ingresso> comprar(@RequestBody @Valid Ingresso ingresso) throws IngressoException, SessaoException, FilmeException, UserException {
		ingressoService.registrarCompra(ingresso);
		return ResponseEntity.ok(ingresso); 
	}
	
	@DeleteMapping("/cancelar/{id}")
	public ResponseEntity<Ingresso> cancelar(@PathVariable Long id) {
		
		try {
			Ingresso ingresso = ingressoService.cancelarCompra(id);
			return ResponseEntity.accepted().body(ingresso);
		} catch (IngressoException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Ingresso> pesquisar(@PathVariable Long id) throws IngressoException {
		Ingresso ingresso = ingressoService.buscar(id);
		return ResponseEntity.ok(ingresso);
	}

}
