package com.cine.monteiro.integracao;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.cine.monteiro.domain.repository.FilmeRepository;
import com.cine.monteiro.domain.repository.GeneroRepository;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FilmeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private FilmeRepository filmeRepository;
	@MockBean
	private GeneroRepository generoRepository;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		mapper.registerModule(new JavaTimeModule());
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	@Test
	@WithMockUser(username="user_admin", authorities = {"ADMIN"})
	public void listar() throws Exception {
		mockMvc.perform(
				get("/filme/listar"))
				.andExpect(status().isOk()).andReturn();
	}

}
