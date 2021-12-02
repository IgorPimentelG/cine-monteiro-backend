package com.cine.monteiro.integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.cine.monteiro.domain.enums.ClassificacaoEtaria;
import com.cine.monteiro.domain.enums.Legenda;
import com.cine.monteiro.domain.enums.Projecao;
import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.repository.FilmeRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
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

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		mapper.registerModule(new JavaTimeModule());
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}

	@Test
	@WithMockUser(username = "user_admin", authorities = { "ADMIN" })
	public void criar_201() throws Exception {

		Filme filme = new Filme();
		filme.setTitulo("O Alto da compadecida");
		filme.setDuracao(120L);

		// convertendo objeto para json
		String json = mapper.writeValueAsString(filme);

		MvcResult result = this.mockMvc.perform(
				MockMvcRequestBuilders.post("/filme/cadastrar").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		assertEquals(result.getResponse().getStatus(), 201);
	}

	@Test
	@WithMockUser(username = "user_admin", authorities = { "ADMIN" })
	public void criar_erro_400() throws Exception {

		Filme filme = new Filme();
		filme.setTitulo("O Alto da compadecida");
		filme.setDuracao(12L);

		// convertendo objeto para json
		String json = mapper.writeValueAsString(filme);

		MvcResult result = this.mockMvc.perform(
				MockMvcRequestBuilders.post("/filme/cadastrar").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getStatus(), 400);
	}

	@Test
	@WithMockUser(username = "user_admin", authorities = { "ADMIN" })
	public void listar_200() throws Exception {

		List<Filme> filmes = new ArrayList<Filme>();

		Filme filme = new Filme();
		filme.setTitulo("Homem Aranha");
		filme.setSinopse("sem sinopse");
		filme.setProjecao(Projecao.PROJECAO_2D);
		filme.setLegenda(Legenda.PORTUGUES);
		filme.setClassificacaoEtaria(ClassificacaoEtaria.MAIOR_OU_IGUAL_12);
		filme.setDuracao(120L);

		filmes.add(filme);

		when(filmeRepository.findAll()).thenReturn(filmes);

		MvcResult result = mockMvc.perform(get("/filme/listar")).andExpect(status().isOk()).andReturn();
		
		assertEquals(result.getResponse().getStatus(), 200);
	}

}
