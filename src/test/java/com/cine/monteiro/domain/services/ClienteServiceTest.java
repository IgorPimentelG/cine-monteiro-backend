package com.cine.monteiro.domain.services;
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
import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.domain.repository.ClienteRepository;


@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
public class ClienteServiceTest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Cliente cliente;
	
	//método vai rodar antes e depois de cada teste
	@BeforeEach
	public void init() {
		cliente = new Cliente("12388400430", "Tadeu", "99885564", LocalDate.now(), "testes@gmail.com", "teste123");
	}

	@Test
	public void A_testSalvar() {
		clienteRepository.save(cliente);
		Cliente getCliente = clienteRepository.findByEmail("testes@gmail.com");
		assertNotNull(getCliente);
	}
	
	@Test
	public void B_testDeletar() {
		Long id = 1L;
		boolean existeAntesDeDeletar= clienteRepository.findById(id).isPresent();
		clienteRepository.deleteById(id);
		boolean naoExisteDepoisDeDeletar = clienteRepository.findById(id).isPresent();
		assertTrue(existeAntesDeDeletar);
		assertFalse(naoExisteDepoisDeDeletar);
	}
	
	@Test
	public void C_testListar() {
		clienteRepository.save(cliente);
		List<Cliente> clientes = clienteRepository.findAll();
		assertEquals(1, clientes.size());
	}

	@Test
	public void D_testPesquisar() {
		Cliente getCliente = clienteRepository.findById(2L).get();
		assertNotNull(getCliente);
	}

	@Test
	public void E_testUpdate() {
		Cliente getCliente = clienteRepository.findById(2L).get();
		getCliente.setEmail("email@gmail.com");
		clienteRepository.save(getCliente);
		
		Cliente getClienteAlterado = clienteRepository.findById(2L).get();
		assertEquals("email@gmail.com", getClienteAlterado.getEmail());
	}
}
