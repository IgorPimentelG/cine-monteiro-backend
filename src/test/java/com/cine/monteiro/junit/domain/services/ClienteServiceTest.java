package com.cine.monteiro.junit.domain.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.UserRepository;



@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
public class ClienteServiceTest {
	
	@Autowired
	private UserRepository userRepository;
	
	private User cliente;
	
	//método vai rodar antes e depois de cada teste
	@BeforeEach
	public void init() {
		cliente = new User("12388400430", "Tadeu", "99885564", LocalDate.now(), "testes@gmail.com", "teste123", "ROLE_USER");
	}

	@Test
	public void testCadastrar() {
		userRepository.save(cliente);
		User getCliente = userRepository.findByEmail("testes@gmail.com");
		assertNotNull(getCliente);
	}
	
	@Test
	public void testDeletar() {
		Long id = 1L;
		boolean existeAntesDeDeletar= userRepository.findById(id).isPresent();
		userRepository.deleteById(id);
		boolean naoExisteDepoisDeDeletar = userRepository.findById(id).isPresent();
		assertTrue(existeAntesDeDeletar);
		assertFalse(naoExisteDepoisDeDeletar);
	}
	
	@Test
	public void testListar() {
		userRepository.save(cliente);
		List<User> clientes = userRepository.findAll();
		assertEquals(1, clientes.size());
	}

//	@Test
//	public void testPesquisar() {
//
//	}
//
//	@Test
//	public void testUpdate() {
//
//	}

}
