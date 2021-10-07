package com.cine.monteiro.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.model.users.Cliente;
import com.cine.monteiro.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente save(Cliente cliente) {
		clienteRepository.save(cliente);
		return cliente;
	}
	
	public Cliente deletar(Long id) {
		Cliente clienteDeletado = clienteRepository.findById(id).get();
		clienteRepository.deleteById(id);
		return clienteDeletado;
		
	}
	
	public Cliente update(Cliente cliente) {
		Cliente clienteDesatualizado = clienteRepository.findById(cliente.getId()).get();
		BeanUtils.copyProperties(cliente, clienteDesatualizado, "id");
		clienteRepository.save(cliente);
		return cliente;
		
	}
	
	public Cliente pesquisar(Long id) {
		Cliente cliente = clienteRepository.findById(id).get();
		return cliente;
	}
	
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
}
