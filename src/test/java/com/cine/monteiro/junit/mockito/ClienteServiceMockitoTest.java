package com.cine.monteiro.junit.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.model.user.Profile;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.UserRepository;
import com.cine.monteiro.domain.services.UserService;
import com.cine.monteiro.exception.UserException;

@SpringBootTest
@RunWith(SpringRunner.class)

public class ClienteServiceMockitoTest {

	@Autowired
	private UserService service;
	
	@MockBean
	private UserRepository repository;
	
	@Test
	public void getUsersTest() throws UserException {
		when(repository.findAll()).thenReturn(Stream.of(new User("12388400430", "Tadeu", "99885564", LocalDate.now(), "testes@gmail.com", "teste123", new ArrayList<Profile>()),
				new User("12388400420", "Marcelo", "99662354", LocalDate.now(), "testes2@gmail.com", "teste345", new ArrayList<Profile>())).collect(Collectors.toList()));
			assertEquals(2, service.listar().size());
	}
	
	@Test
	public void getUsersByEmail() {
		String email = "mockito123@gmail.com";
		when(repository.findByEmail(email)).thenReturn((User) Stream.of(new User("12388400430", "Tadeu", "99885564", LocalDate.now(), "mockito123@gmail.com", "teste123", new ArrayList<Profile>())).collect(Collectors.toList()));
	
		assertEquals("mockito123@gmail.com", repository.findByEmail(email));
	}
	
	@Test
	public void saveUserTest() throws UserException {
		User user = new User("12388400430", "Tadeu", "99885564", LocalDate.now(), "mockito123@gmail.com", "teste123", new ArrayList<Profile>());
		when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.salvar(user));
	}
	
	@Test
	public void deleteUserTest() {
		User user = new User("12388400430", "Tadeu", "99885564", LocalDate.now(), "mockito123@gmail.com", "teste123", new ArrayList<Profile>());
		repository.delete(user);
		
		verify(repository, times(1)).delete(user);
	}
}
