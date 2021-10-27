package com.cine.monteiro.utils;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.cine.monteiro.domain.model.user.Administrador;
import com.cine.monteiro.domain.model.user.Cliente;

class UserUtilsTest {

	private static Cliente cliente;
	private static UserUtils userUtils;
	private static Administrador administrador;
	
	@BeforeAll
	public static void init() {
		userUtils = new UserUtils();
		
		cliente = new Cliente();
		cliente.setEmail("cliente@gmail.com");
		cliente.setNome("cliente1");
		cliente.setPassword("&Aabc123");
		cliente.setCPF("45454545");
		cliente.setTelefone("89898989");
		
		administrador = new Administrador();
		administrador.setEmail("admin@gmail.com");
		administrador.setNome("Admin1");
		administrador.setPassword("@Login01");
	}
	
	@Test
	public void testValidarPassword() {
		assertDoesNotThrow(() -> userUtils.validarPassword(cliente.getPassword()));
	}

	@Test
	public void testValidarEmail() {
		assertDoesNotThrow(() -> userUtils.validarEmail(cliente.getEmail()));
	}
	
	@Test
	public void testCliente() {
	
	}
	
	@Test
	public void testAdmin() {
		
	}

	@Test
	public void testAutenticarCliente() {
		
	}

	@Test
	public void testAutenticarAdmin() {
	}

}
