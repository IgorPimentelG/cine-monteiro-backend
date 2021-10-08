package com.cine.monteiro.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cine.monteiro.model.users.Cliente;
import com.cine.monteiro.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Cliente save(Cliente cliente) {
		cliente.setPassword(encoder.encode(cliente.getPassword()));
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
	
	public Boolean validarSenha(String email, String password){
		Optional<Cliente> optClie = clienteRepository.findByEmail(email);
		if(optClie.isEmpty()) {
			return false;
		}
		boolean valide = encoder.matches(password, optClie.get().getPassword());
		boolean result = (valide) ? true : false; 
		return result;
	}
}
