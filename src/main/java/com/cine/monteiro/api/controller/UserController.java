package com.cine.monteiro.api.controller;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.services.UserService;
import com.cine.monteiro.exception.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired private UserService userService;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<User> cadastrar(@Valid @RequestBody User user) throws UserException {
		userService.salvar(user);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/recuperar-conta")
	public ResponseEntity<String> recuperar(@RequestBody String email) {
		
		JSONObject json = new JSONObject(email);
		email = json.getString("email");
		
		try {
			userService.recuperarPassword(email);
			return new ResponseEntity<String>("E-mail Enviado com Sucesso!", HttpStatus.ACCEPTED);
		} catch (UserException error) {
			return new ResponseEntity<String>("Usuário Não Encontrado!", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<User> deletar(@PathVariable Long id) throws UserException {
		User userDeletado = userService.deletar(id);
		return ResponseEntity.ok(userDeletado);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<User> atualizar(@RequestBody User user) throws UserException {
		User userAtualizado = userService.atualizar(user);
		return ResponseEntity.ok(userAtualizado);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<User>> listar() throws UserException {
		List<User> users = userService.listar();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/pesquisar/{id}")
	public ResponseEntity<User> pesquisar(@PathVariable Long id) throws UserException {
		User userPesquisado = userService.pesquisar(id);
		return ResponseEntity.ok(userPesquisado);
	}
	
	@GetMapping("/autenticar")
	public ResponseEntity<User> validarSenha(@RequestParam String email, @RequestParam String password) throws UserException {
		User user = userService.autenticar(email, password);
		return ResponseEntity.ok(user);
	}
}
