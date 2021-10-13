package com.cine.monteiro.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cine.monteiro.model.users.Cliente;
import com.cine.monteiro.services.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/cadastrar")
	public Cliente cadastrar(@RequestBody Cliente cliente) {
		Cliente clienteCadastrado = clienteService.save(cliente);
		return clienteCadastrado;
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Cliente> deletar(@PathVariable Long id) {
		Cliente clienteDeletado = clienteService.deletar(id);
		return clienteDeletado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(clienteDeletado);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Cliente> atualizar(@RequestBody Cliente cliente) {
		Cliente clienteAtualizado = clienteService.update(cliente);
		return clienteAtualizado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(clienteAtualizado);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Cliente>> listar() {
		List<Cliente> clientes = clienteService.listar();
		return clientes.size() == 0 ? ResponseEntity.notFound().build() : ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Cliente> pesquisar(@PathVariable Long id) {
		Cliente clientePesquisado = clienteService.pesquisar(id);
		return clientePesquisado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(clientePesquisado);
	}
	
	@GetMapping("/autenticar")
	public ResponseEntity<Boolean> autenticar(@RequestParam String email, @RequestParam String password){
		boolean validar = clienteService.validarSenha(email, password);
		HttpStatus status = (validar) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status).body(validar);
	}

}
