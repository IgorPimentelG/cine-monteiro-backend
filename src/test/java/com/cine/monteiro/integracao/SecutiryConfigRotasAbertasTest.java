package com.cine.monteiro.integracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
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
import com.cine.monteiro.mail.EmailConfig;
import com.cine.monteiro.utils.UserUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecutiryConfigRotasAbertasTest {

	private MockMvc mockMvc;

	@Autowired private ObjectMapper mapper;
	@Autowired private WebApplicationContext webApplicationContext;

	@MockBean private UserRepository userRepositoryMock;
	@MockBean private SalaRepository salaRepository;
	@MockBean private EmailConfig emailConfigMock;
	@SpyBean private UserUtils userUtilsMock;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		mapper.registerModule(new JavaTimeModule());
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}

	// Rotas Aberta
	@Test
	@WithAnonymousUser
	public void t1_recuperarEmailSucess() throws Exception {

		User userMock = mock(User.class);

		when(userMock.getEmail()).thenReturn("cinemonteiro.admin@gmail.com");
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(userMock);
		when(emailConfigMock.enviarEmail(anyString(), anyString(), anyString())).thenReturn(true);

		Object emailObject = new Object() {
			String email = "cinemonteiro.admin@gmail.com";
		};

		String body = mapper.writeValueAsString(emailObject);
		
		MvcResult response = mockMvc.perform(
			  put("/user/recuperar-conta")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(body)
			  .characterEncoding("utf-8"))
		.andExpect(status().isAccepted()).andReturn();
	  
		assertEquals(202, response.getResponse().getStatus()); 
		
		verify(userUtilsMock, times(1)).gerarNovaSenha();
		verify(userMock, times(1)).setPassword(anyString()); 
		verify(userRepositoryMock, times(1)).save(any(User.class));
		 
	}

	@Test
	@WithAnonymousUser
	public void t2_recuperarEmailError() throws Exception {
		
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(null);

		Object emailObject = new Object() {
			String email = "cinemonteiro.client@gmail.com";
		};

		String body = mapper.writeValueAsString(emailObject);
		
		MvcResult response = mockMvc.perform(
			  put("/user/recuperar-conta")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(body)
			  .characterEncoding("utf-8"))
		.andExpect(status().isNotFound()).andReturn();
	  
		assertEquals(404, response.getResponse().getStatus());
		
		verify(userUtilsMock, never()).gerarNovaSenha();
		verify(emailConfigMock, never()).enviarEmail(anyString(), anyString(), anyString());
		verify(userRepositoryMock, never()).save(any(User.class));
	}
	
}
