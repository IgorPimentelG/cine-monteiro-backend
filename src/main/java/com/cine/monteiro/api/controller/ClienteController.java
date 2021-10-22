package com.cine.monteiro.api.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.domain.services.ClienteService;
import com.cine.monteiro.exception.UserException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/cadastrar")
	public Cliente cadastrar(@RequestBody @Valid Cliente cliente) throws UserException {
		return clienteService.salvar(cliente);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Cliente> deletar(@PathVariable Long id) throws UserException {		
		Cliente clienteDeletado = clienteService.deletar(id);
		return ResponseEntity.ok(clienteDeletado);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Cliente> atualizar(@RequestBody @Valid Cliente cliente) throws UserException {
		Cliente clienteAtualizado = clienteService.update(cliente);
		return ResponseEntity.ok(clienteAtualizado);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Cliente>> listar() throws UserException {
		List<Cliente> clientes = clienteService.listar();
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Cliente> pesquisar(@PathVariable Long id) throws UserException  {
		Cliente clientePesquisado = clienteService.pesquisar(id);
		return ResponseEntity.ok(clientePesquisado);
	}
	
	/*
	@GetMapping("/autenticar")
	public ResponseEntity<Boolean> autenticar(@RequestParam String email, @RequestParam String password){
		boolean validar = clienteService.validarSenha(email, password);
		HttpStatus status = (validar) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status).body(validar);
	}
	*/

}
