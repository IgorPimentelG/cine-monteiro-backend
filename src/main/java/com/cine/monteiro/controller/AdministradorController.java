package com.cine.monteiro.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.exception.UserException;
import com.cine.monteiro.model.users.Administrador;
import com.cine.monteiro.services.AdministradorService;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private AdministradorService administradorService;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Administrador> cadastrar(@RequestBody Administrador administrador) throws UserException {
		administradorService.salvar(administrador);
		return ResponseEntity.ok(administrador);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Administrador> deletar(@PathVariable Long id) throws UserException {
		Administrador administradorDeletado = administradorService.deletar(id);
		return ResponseEntity.ok(administradorDeletado);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Administrador> atualizar(@RequestBody Administrador administrador) throws UserException {
		Administrador administradorAtualizado = administradorService.update(administrador);
		return ResponseEntity.ok(administradorAtualizado);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Administrador>> listar() throws UserException {
		List<Administrador> administradors = administradorService.listar();
		return ResponseEntity.ok(administradors);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Administrador> pesquisar(@PathVariable Long id) throws UserException {
		Administrador administradorPesquisado = administradorService.pesquisar(id);
		return ResponseEntity.ok(administradorPesquisado);
	}
	
	@GetMapping("/autenticar")
	public ResponseEntity<Administrador> validarSenha(@RequestParam String email, @RequestParam String password) throws UserException {
		Administrador administrador = administradorService.autenticar(email, password);
		return ResponseEntity.ok(administrador);
	}
}
