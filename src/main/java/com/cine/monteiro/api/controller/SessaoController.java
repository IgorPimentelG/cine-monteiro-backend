package com.cine.monteiro.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.services.SessaoService;
import com.cine.monteiro.exception.*;

@RestController
@RequestMapping("/sessao")
@CrossOrigin(origins = "http://localhost:4200")
public class SessaoController {
	
	@Autowired
	private SessaoService sessaoService;

	@PostMapping("/cadastrar")
	public ResponseEntity<Sessao> cadastrar(@Valid @RequestBody Sessao sessao) throws SessaoException, SalaException, FilmeException { 
		sessaoService.cadastrar(sessao);
		return ResponseEntity.ok(sessao);
	}
	
	@PutMapping("/interromper/{id}")
	public ResponseEntity<Sessao> interromperEmUmDia(@PathVariable Long id) throws SessaoException {
		Sessao sessao = sessaoService.interromperEmUmDia(id);
		return ResponseEntity.ok(sessao);
	}
	
	@PutMapping("/desativar/{id}")
	public ResponseEntity<Sessao> desativar(@PathVariable("id") Long id) throws SessaoException {
		Sessao sessao = sessaoService.desativar(id);
		return ResponseEntity.ok(sessao);
	}
	
	@GetMapping("/listar-dia-atual")
	public ResponseEntity<List<Sessao>> listarDiaAtual() throws SessaoException {
		List<Sessao> sessoes = sessaoService.listarDiaAtual();
		return ResponseEntity.ok(sessoes);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Sessao>> listarAll() throws SessaoException {
		List<Sessao> sessoes = sessaoService.listar();
		return ResponseEntity.ok(sessoes);
	}
	
	@GetMapping("/listar-por-sala/{idSala}")
	public ResponseEntity<List<Sessao>> listarPorSala(@PathVariable("idSala") Long id) throws SessaoException, SalaException {
		List<Sessao> sessoes = sessaoService.listarPorSala(id);
		return ResponseEntity.ok(sessoes);
	}

}
