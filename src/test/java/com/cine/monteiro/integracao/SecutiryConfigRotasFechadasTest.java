package com.cine.monteiro.integracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cine.monteiro.domain.enums.ClassificacaoEtaria;
import com.cine.monteiro.domain.enums.Legenda;
import com.cine.monteiro.domain.enums.Projecao;
import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.model.cinema.Genero;
import com.cine.monteiro.domain.model.cinema.Ingresso;
import com.cine.monteiro.domain.model.cinema.Sala;
import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.repository.IngressoRepository;
import com.cine.monteiro.domain.repository.SalaRepository;
import com.cine.monteiro.domain.repository.SessaoRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecutiryConfigRotasFechadasTest {

	private MockMvc mockMvc;

	@Autowired private ObjectMapper mapper;
	@Autowired private WebApplicationContext webApplicationContext;
	
	@MockBean private SalaRepository salaRepositoryMock;
	@MockBean private SessaoRepository sessaoRepositoryMock;
	@MockBean private IngressoRepository ingressoRepositoryMock;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		mapper.registerModule(new JavaTimeModule());
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	// Rotas Admin
	@Test
	public void t1_deletarSalaSucess() throws Exception {
		
		Sala sala = new Sala("Sala 01", 40);
		sala.setId(1L);
		
		Sessao sessao = new Sessao();
		sessao.setId(1L);
		sessao.setAtiva(false);
		
		sala.adicionarSessao(sessao);
		
		when(salaRepositoryMock.findById(anyLong())).thenReturn(Optional.of(sala));
		
		MvcResult response = mockMvc.perform(
				delete("/sala/deletar/{id}", 1).with(httpBasic("cinemonteiro.admin@gmail.com", "UO9tbkO#")))
				.andExpect(status().isOk())
				.andReturn();
		
		assertEquals(200, response.getResponse().getStatus());
		assertEquals(mapper.writeValueAsString(sala), response.getResponse().getContentAsString());
		
		verify(salaRepositoryMock, times(1)).delete(any(Sala.class));
	}
	

	@Test
	public void t2_deletarSalaError() throws Exception {
		
		Sala sala = new Sala("Sala 01", 40);
		sala.setId(1L);
		
		Sessao sessao = new Sessao();
		sessao.setId(1L);
		sessao.setAtiva(false);
		
		sala.adicionarSessao(sessao);
		
		when(salaRepositoryMock.findById(anyLong())).thenReturn(Optional.of(sala));
		
		MvcResult response = mockMvc.perform(
				delete("/sala/deletar/{id}", 1).with(httpBasic("cinemonteiro.client@gmail.com", "UO9tbkO#")))
				.andExpect(status().isForbidden())
				.andReturn();
		
		assertEquals(403, response.getResponse().getStatus());
		assertNotEquals(mapper.writeValueAsString(sala), response.getResponse().getContentAsString());
		
		verify(salaRepositoryMock, never()).delete(any(Sala.class));
	}
	
	@Test
	public void t3_loginError() throws Exception {
		
		Sala sala = new Sala("Sala 01", 40);
		sala.setId(1L);
		
		Sessao sessao = new Sessao();
		sessao.setId(1L);
		sessao.setAtiva(false);
		
		sala.adicionarSessao(sessao);
		
		when(salaRepositoryMock.findById(anyLong())).thenReturn(Optional.of(sala));
		
		MvcResult response = mockMvc.perform(
				delete("/sala/deletar/{id}", 1).with(httpBasic("cinemonteiro@gmail.com", "UO9tbkO#")))
				.andExpect(status().isUnauthorized())
				.andReturn();
		
		assertEquals(401, response.getResponse().getStatus());
		
		verify(salaRepositoryMock, never()).delete(any(Sala.class));
	}

	// Rotas Cliente
	@Test
	public void t4_deletarIngressoSucess() throws Exception {
		
		Sessao sessao = new Sessao();
		sessao.setId(1L);
		sessao.setHoraDeInicioExibicao(LocalTime.parse("21:30:00"));
		
		Ingresso ingresso = new Ingresso();
		ingresso.setId(1L);
		ingresso.setSessao(sessao);	
		
		when(ingressoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(ingresso));
		
		MvcResult response = mockMvc.perform(
			delete("/ingresso/cancelar/{id}", 1).with(httpBasic("cinemonteiro.client@gmail.com", "UO9tbkO#")))	
			.andExpect(status().isAccepted())
			.andReturn();
		
		assertEquals(202, response.getResponse().getStatus());
		assertEquals(mapper.writeValueAsString(ingresso), response.getResponse().getContentAsString());
		
		verify(ingressoRepositoryMock, times(1)).delete(any(Ingresso.class));
	
	}
	
	@Test
	public void t5_deletarIngressoError() throws Exception {
		
		Sessao sessao = new Sessao();
		sessao.setId(1L);
		sessao.setHoraDeInicioExibicao(LocalTime.parse("21:30:00"));
		
		Ingresso ingresso = new Ingresso();
		ingresso.setId(1L);
		ingresso.setSessao(sessao);	
		
		when(ingressoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(ingresso));
		
		MvcResult response = mockMvc.perform(
			delete("/ingresso/cancelar/{id}", 1).with(httpBasic("cinemonteiro.admin@gmail.com", "UO9tbkO#")))	
			.andExpect(status().isForbidden())
			.andReturn();
		
		assertEquals(403, response.getResponse().getStatus());

		verify(ingressoRepositoryMock, never()).delete(any(Ingresso.class));
	}
	
	// Rotas Cliente e Admin
	
	public void t6_listarSessoesDoDia() throws Exception {
		
		List<Sessao> sessoes = factorySessao();
		
		when(sessaoRepositoryMock.buscarSessoesDoDia(any(LocalTime.class))).thenReturn(sessoes);
		
		MvcResult responseAdmin = mockMvc.perform(
				get("/sessao/listar-dia-atual").with(httpBasic("cinemonteiro.admin@gmail.com", "UO9tbkO#")))	
				.andExpect(status().isOk())
				.andReturn();
		
		MvcResult responseClient = mockMvc.perform(
				get("/sessao/listar-dia-atual").with(httpBasic("cinemonteiro.client@gmail.com", "UO9tbkO#")))	
				.andExpect(status().isOk())
				.andReturn();
		
		verify(sessaoRepositoryMock, times(2)).buscarSessoesDoDia(any(LocalTime.class));
		
		String jsonEsperado = mapper.writeValueAsString(sessoes);
		
		assertEquals(200, responseAdmin.getResponse().getStatus());
		assertEquals(200, responseClient.getResponse().getStatus());
		assertEquals(jsonEsperado, responseAdmin.getResponse().getContentAsString());
		assertEquals(jsonEsperado, responseClient.getResponse().getContentAsString());
		
	}
	
	private List<Sessao> factorySessao() {
		
		Sala sala = new Sala("Sala 05", 40);
		sala.setId(1L);
		
		Genero genero = new Genero("Ação", "Contém cenas de violÊNCIA");
		genero.setId(1L);
		
		Filme filme = new Filme(
			"13 Horas: Os Soldados Secretos de Benghazi",
			"Em 11 de setembro de 2012, militantes islâmicos atacam o Consulado dos EUA em Benghazi, na Líbia, durante a visita de um embaixador americano.",
			genero,
			156L,
			ClassificacaoEtaria.MAIOR_OU_IGUAL_16,
			Legenda.NONE,
			Projecao.PROJECAO_2D
		);
		filme.setId(1L);
		
		Sessao s1 = new Sessao(
				filme,
				sala,
				new BigDecimal("17.80"),
				LocalTime.parse("20:30:00"),
				LocalTime.parse("23:50:00"),
				LocalDate.parse("2021-12-02"),
				LocalDate.parse("2021-12-20"),
				40,
				true
		);
		s1.setId(1L);
		
		Sessao s2 = new Sessao(
				filme,
				sala,
				new BigDecimal("17.80"),
				LocalTime.parse("19:00:00"),
				LocalTime.parse("20:00:00"),
				LocalDate.parse("2021-12-02"),
				LocalDate.parse("2021-12-20"),
				40,
				true
		);
		s2.setId(2L);
		
		sala.adicionarSessao(s1);
		sala.adicionarSessao(s2);
		
		return new ArrayList<Sessao>() {
			{ add(s1); }
			{ add(s2); }
		};
	}
	
}
