package com.cine.monteiro.domain.services;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.domain.repository.ClienteRepository;
import com.cine.monteiro.exception.UserException;
import com.cine.monteiro.utils.UserUtils;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private UserUtils userUtils;
	
	public Cliente salvar(Cliente cliente) throws UserException, ValidationException {
		
		// Validações
		userUtils.validarEmail(cliente.getEmail());
		userUtils.validarPassword(cliente.getPassword());
		
		if(clienteRepository.findByEmail(cliente.getEmail()) != null) {
			throw new UserException("USUÁRIO JÁ CADASTRADO!");
		}
		
		cliente.setPassword(userUtils.encodePassword(cliente.getPassword()));				// Encriptar Senha
		
		return clienteRepository.save(cliente);
	}
	
	public Cliente deletar(Long id) throws UserException {
		Cliente cliente = clienteRepository.findById(id).get();
		
		validarRetorno(cliente);

		clienteRepository.deleteById(id);
		return cliente;
	}
	
	public Cliente update(Cliente cliente) throws UserException {
		Cliente clienteDesatualizado = clienteRepository.findById(cliente.getId()).get();
		
		validarRetorno(clienteDesatualizado);
		
		BeanUtils.copyProperties(cliente, clienteDesatualizado, "id");
		return clienteRepository.save(cliente);
	}
	
	public Cliente pesquisar(Long id) throws UserException {
		Cliente cliente = clienteRepository.findById(id).get();
		
		validarRetorno(cliente);
		
		return cliente;
	}
	
	public List<Cliente> listar() throws UserException {
		
		List<Cliente> clientes = clienteRepository.findAll();
		
		if(clientes.isEmpty()) {
			throw new UserException("NÃO EXISTE USUÁRIOS CADASTRADOS!");
		}
		
		return clientes;
	}
	
	public Cliente autenticar(String email, String password) throws UserException {
		return userUtils.autenticarCliente(email, password);
	}
	
	private void validarRetorno(Cliente cliente) throws UserException {
		if(cliente == null) {
			throw new UserException("USUÁRIO NÃO ENCONTRADO!");
		}
	}
	
}
