package com.cine.monteiro.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cine.monteiro.model.users.Administrador;
import com.cine.monteiro.repository.AdministradorRepository;

@Service
public class AdministradorService {
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Administrador save(Administrador administrador) {
		administrador.setPassword(encoder.encode(administrador.getPassword()));
		administradorRepository.save(administrador);
		return administrador;
	}
	
	public Administrador deletar(Long id) {
		Administrador administradorDeletado = administradorRepository.findById(id).get();
		administradorRepository.deleteById(id);
		return administradorDeletado;
		
	}
	
	public Administrador update(Administrador administrador) {
		Administrador administradorDesatualizado = administradorRepository.findById(administrador.getId()).get();
		BeanUtils.copyProperties(administrador, administradorDesatualizado, "id");
		administradorRepository.save(administrador);
		return administrador;
		
	}
	
	public Administrador pesquisar(Long id) {
		Administrador administrador = administradorRepository.findById(id).get();
		return administrador;
	}
	
	public List<Administrador> listar() {
		return administradorRepository.findAll();
	}
	
	public Boolean validarSenha(String email, String password){
		Optional<Administrador> optAdm = administradorRepository.findByEmail(email);
		if(optAdm.isEmpty()) {
			return false;
		}
		boolean valide = encoder.matches(password, optAdm.get().getPassword());
		boolean result = (valide) ? true : false; 
		return result;
	}
}
