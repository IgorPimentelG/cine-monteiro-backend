package com.cine.monteiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.exception.SalaException;
import com.cine.monteiro.exception.SessaoException;
import com.cine.monteiro.model.cinema.Sessao;
import com.cine.monteiro.services.SessaoService;

@RestController
@RequestMapping("/sessao")
public class SessaoController {
	
	@Autowired
	private SessaoService sessaoService;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Sessao> cadastrar(@RequestBody Sessao sessao)throws SessaoException, SalaException {
		sessaoService.cadastrar(sessao);
		return ResponseEntity.ok(sessao);
	}
	

}
