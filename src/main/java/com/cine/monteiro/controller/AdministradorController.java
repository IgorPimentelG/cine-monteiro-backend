package com.cine.monteiro.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cine.monteiro.model.users.Administrador;
import com.cine.monteiro.services.AdministradorService;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private AdministradorService administradorService;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Administrador> cadastrar(@RequestBody Administrador administrador) {
		Administrador administradorCadastrado = administradorService.save(administrador);
		return administradorCadastrado == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(administrador);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Administrador> deletar(@PathVariable Long id) {
		Administrador administradorDeletado = administradorService.deletar(id);
		return administradorDeletado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(administradorDeletado);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Administrador> atualizar(@RequestBody Administrador administrador) {
		Administrador administradorAtualizado = administradorService.update(administrador);
		return administradorAtualizado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(administradorAtualizado);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Administrador>> listar() {
		List<Administrador> administradors = administradorService.listar();
		return administradors.size() == 0 ? ResponseEntity.notFound().build() : ResponseEntity.ok(administradors);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<Administrador> pesquisar(@PathVariable Long id) {
		Administrador administradorPesquisado = administradorService.pesquisar(id);
		return administradorPesquisado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(administradorPesquisado);
	}
	
	@GetMapping("/validarSenha")
	public ResponseEntity<Boolean> validarSenha(@RequestParam String email, @RequestParam String password){
		boolean validar = administradorService.validarSenha(email, password);
		HttpStatus status = (validar) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status).body(validar);
	}
}
