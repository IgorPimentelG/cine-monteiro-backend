package com.cine.monteiro.junit.utils;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.UserRepository;
import com.cine.monteiro.utils.UserUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
public class UserUtilsTest {

	@Autowired
	private UserUtils userUtils;
	
	@Autowired
	private UserRepository userRepository;

	
	private static User cliente;
	private static User administrador;
	
	@BeforeAll
	public static void init() {
		cliente = new User();
		cliente.setEmail("cliente@gmail.com");
		cliente.setNome("cliente1");
		cliente.setDataNascimento(LocalDate.now());
		cliente.setPassword("&Aabc123");
		cliente.setCPF("133.069.590-90"); 
		cliente.setTelefone("(083) 95014-0125");
	
		administrador = new User();
		administrador.setEmail("administrador@gmail.com");
		administrador.setNome("administrador");
		administrador.setDataNascimento(LocalDate.now());
		administrador.setPassword("@Senha123");
		administrador.setCPF("730.236.470-20");
		administrador.setTelefone("(057) 92176-6000");
	}
	
	@Test
	public void testValidarPassword() {
		assertDoesNotThrow(() -> userUtils.validarPassword(cliente.getPassword()));
		assertDoesNotThrow(() -> userUtils.validarPassword(administrador.getPassword()));
	}

	@Test
	public void testValidarEmail() {
		assertDoesNotThrow(() -> userUtils.validarEmail(cliente.getEmail()));
		assertDoesNotThrow(() -> userUtils.validarEmail(administrador.getEmail()));
	}
	
	@Test
	public void testValidarTelefone() {
		assertDoesNotThrow(() -> userUtils.validarTelefone(cliente.getTelefone()));
		assertDoesNotThrow(() -> userUtils.validarTelefone(administrador.getTelefone()));
	}
	
	@Test
	public void testAutenticarCliente(){
		String senha = cliente.getPassword();
		cliente.setPassword(userUtils.encodePassword(cliente.getPassword()));
		
		userRepository.save(cliente);
		User getCliente = userRepository.findByEmail("cliente@gmail.com");
		assertNotNull(getCliente);
		
		User cli = assertDoesNotThrow(() -> userUtils.autenticar(getCliente.getEmail(),senha));
		assertNotNull(cli);
	}

	@Test
	public void testAutenticarAdmin() {
		String senha = administrador.getPassword();
		administrador.setPassword(userUtils.encodePassword(administrador.getPassword()));
		
		userRepository.save(administrador);
		User getAdmin = userRepository.findByEmail("administrador@gmail.com");
		assertNotNull(getAdmin);
		
		User adm = assertDoesNotThrow(() -> userUtils.autenticar(getAdmin.getEmail(),senha));
		assertNotNull(adm);
	}

}
