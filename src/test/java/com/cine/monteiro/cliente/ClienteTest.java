package com.cine.monteiro.cliente;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.domain.repository.ClienteRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
@DataJpaTest
public class ClienteTest {
	
	@Autowired
	private ClienteRepository repo;
	
	@Test
	public void criarClienteTest() {
		Cliente cliente = new Cliente("12388400430", "Tadeu", "99885564", null, "testes@gmail.com", "teste123");
		Cliente clienteSalvo = repo.save(cliente);
		
		assertNotNull(clienteSalvo);
		
	}
	
	@Test
	public void encontrarClientePeloNomeTest() {
		
		String nome = "Tadeu";
		Cliente nomeCliente = repo.findByName(nome);
		
		assertThat(nomeCliente.getNome()).isEqualTo(nome);
	}
	
	@Test
	public void encontrarClientePeloNomeQueNaoExistTest() {
		
		String nome = "Tadeu";
		Cliente nomeCliente = repo.findByName(nome);
		
		assertNull(nomeCliente);
	}
	
	@Test
	public void atualizarClienteTest() {
		
		String nomeCliente = "Henry";
		
		Cliente cliente = new Cliente("12344566723", nomeCliente, "99123465", null, "testandooo123@gmail.com", "quemamou");
		cliente.setId(1L);
		
		repo.save(cliente);
		
		Cliente clienteAtualizado =  repo.findByName(nomeCliente);
		
		assertThat(clienteAtualizado.getNome()).isEqualTo(nomeCliente);
	}
	
	@Test
	public void ListaClientesTest() {
		List<Cliente> clientes = repo.findAll();
		
		for(Cliente cliente : clientes) {
			System.out.println(cliente);
		}
		
		assertThat(clientes).size().isGreaterThan(0);
	};
	
	@Test
	public void deletarClienteTest() {
		Long id = 1L;
		
		boolean existeAntesDeDeletar= repo.findById(id).isPresent();
		
		repo.deleteById(id);
		
		boolean naoExisteDepoisDeDeletar = repo.findById(id).isPresent();
		
		assertTrue(existeAntesDeDeletar);
		assertFalse(naoExisteDepoisDeDeletar);
	}
	
	
}
