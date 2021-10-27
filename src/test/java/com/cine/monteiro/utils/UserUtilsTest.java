package com.cine.monteiro.utils;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.cine.monteiro.domain.model.user.Administrador;
import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.domain.repository.AdministradorRepository;
import com.cine.monteiro.domain.repository.ClienteRepository;

@SpringBootTest
@Transactional
class UserUtilsTest {

	@Autowired
	private UserUtils userUtils;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private AdministradorRepository administradorRepository;
	
	private static Cliente cliente;
	private static Administrador administrador;
	
	@BeforeAll
	public static void init() {
		cliente = new Cliente();
		cliente.setEmail("cliente@gmail.com");
		cliente.setNome("cliente1");
		cliente.setDataNascimento(LocalDate.now());
		cliente.setPassword("&Aabc123");
		cliente.setCPF("133.069.590-90");
		cliente.setTelefone("(083) 95014-0125");
	
		administrador = new Administrador();
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
		
		clienteRepository.save(cliente);
		Cliente getCliente = clienteRepository.findByEmail("cliente@gmail.com");
		assertNotNull(getCliente);
		
		Cliente cli = assertDoesNotThrow(() -> userUtils.autenticarCliente(getCliente.getEmail(),senha));
		assertNotNull(cli);
	}

	@Test
	public void testAutenticarAdmin() {
		String senha = administrador.getPassword();
		administrador.setPassword(userUtils.encodePassword(administrador.getPassword()));
		
		administradorRepository.save(administrador);
		Administrador getAdmin = administradorRepository.findByEmail("administrador@gmail.com");
		assertNotNull(getAdmin);
		
		Administrador adm = assertDoesNotThrow(() -> userUtils.autenticarAdmin(getAdmin.getEmail(),senha));
		assertNotNull(adm);
	}

}
