package com.cine.monteiro.api.controller;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.cine.monteiro.domain.DTO.DTOUserAuth;
import com.cine.monteiro.domain.model.user.AccountCredentials;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.services.UserService;
import com.cine.monteiro.exception.UserException;
import com.cine.monteiro.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired private JwtUtil jwtUtil;
	@Autowired private ObjectMapper mapper;
	@Autowired private UserService userService;
	@Autowired private AuthenticationManager authenticationManager;
	
	@PostMapping("/autenticar")
	public String autenticar(@RequestBody AccountCredentials accountCredentials) throws Exception {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(accountCredentials.getUsername(), accountCredentials.getPassword()));
		} catch(Exception error) {
			throw new UserException("Usuário Inválido.");
		}
		
		String profile = userService.perfilUser(accountCredentials.getUsername());
		String token = jwtUtil.gerarToken(accountCredentials.getUsername());
		
		return mapper.writeValueAsString(new DTOUserAuth(token, profile));
		
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<User> cadastrar(@Valid @RequestBody User user) throws UserException {
		userService.salvar(user);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/recuperar")
	public void recuperar(@RequestBody String email) throws UserException {
		JSONObject json = new JSONObject(email);
		email = json.getString("email");
		userService.recuperarPassword(email);
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
	
}
