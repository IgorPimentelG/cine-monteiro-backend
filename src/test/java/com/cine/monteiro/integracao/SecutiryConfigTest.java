package com.cine.monteiro.integracao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cine.monteiro.domain.model.cinema.Sala;
import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.SalaRepository;
import com.cine.monteiro.domain.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecutiryConfigTest {
	
	
	private MockMvc mockMvc;
	
	@Autowired private ObjectMapper mapper;
	@Autowired private WebApplicationContext webApplicationContext;
	
	@MockBean private UserRepository userRepositoryMock;
	@MockBean private SalaRepository salaRepository;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		mapper.registerModule(new JavaTimeModule());
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	// Rotas Aberta
	@Test
	@WithAnonymousUser
	public void t1_cadastrarSucess() throws Exception {
		
		User user = new User();
		user.setCPF("475.063.007-10");
		user.setNome("Yago Calebe Ramos");
		user.setTelefone("(34) 99971-9957");
		user.setDataNascimento(LocalDate.parse("2002-12-23"));
		user.setEmail("yagocaleberamos_@agenciadbd.com");
		user.setPassword("CEck#GD3HFm874");
		
		String bodyRequest = mapper.writeValueAsString(user);
		
		MvcResult response = mockMvc.perform(
				post("/user/cadastrar").content(bodyRequest)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk()).andReturn();
		
		assertEquals(200, response.getResponse().getStatus());
		verify(userRepositoryMock, times(1)).save(any(User.class));
	}
	
	@Test
	@WithAnonymousUser
	public void t2_cadastrarError() throws Exception {

		User user = new User();
		user.setCPF("475.063");
		user.setNome("Yago Calebe Ramos");
		user.setTelefone("(9971-9957");
		user.setDataNascimento(LocalDate.parse("2002-12-23"));
		user.setEmail("yagocaleberamos_@agenciadbd.com");
		user.setPassword("123456");
		
		String bodyRequest = mapper.writeValueAsString(user);
		
		MvcResult response = mockMvc.perform(
				post("/user/cadastrar").content(bodyRequest)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals(400, response.getResponse().getStatus());
		verify(userRepositoryMock, never()).save(any(User.class));
	}

	// Rotas Admin
	@Test
	@WithMockUser(username="user_admin", authorities = {"ADMIN"})
	public void t3_deletarSalaSucess() throws Exception {
	
	}
	
	public void t4_cadastrarSalaSucess() {
		
	}
	
	
	
	// Rotas Cliente
	
	

}
