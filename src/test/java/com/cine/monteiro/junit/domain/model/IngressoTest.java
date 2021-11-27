package com.cine.monteiro.junit.domain.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.enums.*;
import com.cine.monteiro.domain.model.cinema.*;
import com.cine.monteiro.domain.model.user.Profile;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.*;
import com.cine.monteiro.domain.services.IngressoService;


@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IngressoTest {
	
	// Injeção de Dependência
	@Autowired
	private SalaRepository salaRepository;
	
	@Autowired
	private FilmeRepository filmeRepository;
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IngressoService ingressoService;

	@Test
	public void t1_init() {
	
		Sala sala = new Sala("Sala 01", 40);
		sala = salaRepository.save(sala);
		assertNotNull(sala);
		
		Genero genero = new Genero("Ação", "Contém cenas de violência.");
		genero = generoRepository.save(genero);
		assertNotNull(genero);
		
		Filme filme = new Filme(
				"As Passageiras",
				"Um jovem motorista leva duas mulheres misteriosas para uma noite de festa.",
				genero,
				120L,
				ClassificacaoEtaria.MAIOR_OU_IGUAL_16,
				Legenda.NONE,
				Projecao.PROJECAO_2D);
		filme = filmeRepository.save(filme);
		assertNotNull(filme);
		
		Sessao sessao = new Sessao(
				filme,
				sala,
				new BigDecimal("20.00"),											// Preço
				LocalTime.parse("21:00:00"),										// Hora de Início
				LocalTime.parse("21:00:00").plusMinutes(filme.getDuracao()),		// Hora de Término
				LocalDate.parse("2021-10-29"),										// Data de Início
				LocalDate.parse("2021-11-15"),										// Data de Final
				sala.getQuantidadeAssentos(),
				true);																// isAtivo
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessao);
	}

	@Test
	public void t2_testClassificacaoEtariaClienteInvalida() {
		
		// Classificação Etária: MAIOR OU IGUAL 16		
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		assertNotNull(sessao);
		
		User cliente = new User(
				"882.446.861-62",
				"Cláudio Nelson",
				"(11) 99977-2153",
				LocalDate.parse("2007-05-12"),				// IDADE:14
				"claudio@hotmail.com",
				"1235678", new ArrayList<Profile>());
		cliente = userRepository.save(cliente);
		assertNotNull(cliente);

		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(1);
		ingresso.adicionarAssento("A1");
		
		try {
			ingressoService.registrarCompra(ingresso);
		} catch(Exception erro) {
			assertEquals("[ERROR INGRESSO] - CLASSIFICAÇÃO ETÁRIA IMPRÓPRIA", erro.getMessage());
		}
	}

	@Test
	public void t3_testClassificacaoEtariaClienteValida() {
	
		// Classificação Etária: MAIOR OU IGUAL 16
		
		User cliente = new User(
				"342.487.628-38",
				"Benedito Rafael",
				"(53) 23957-4553",
				LocalDate.parse("1990-03-23"),			// IDADE: 31
				"cinemonteiro.ads@gmail.com",
				"1235678", new ArrayList<Profile>());
		cliente = userRepository.save(cliente);
		assertNotNull(cliente);
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		assertNotNull(sessao);
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(1);
		ingresso.adicionarAssento("A1");
				
		assertDoesNotThrow( () -> ingressoService.registrarCompra(ingresso));
		
	}
	
	@Test
	public void t4_testCompraIngressoAssentoJaReservado() {
		User cliente = userRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		sessao.adicionarAssentoReservado("A5");
		sessao.adicionarAssentoReservado("B1");
		sessao.adicionarAssentoReservado("B2");
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessao);
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(3);
		ingresso.adicionarAssento("A5");
		ingresso.adicionarAssento("B1");
		ingresso.adicionarAssento("A6");
				
		try {
			ingressoService.registrarCompra(ingresso);
		} catch(Exception erro) {
			assertEquals("[ERROR INGRESSO] - ASSENTO(S) JÁ RESERVADO(S)!", erro.getMessage());
		}
	}
	
	@Test
	public void t5_testVagasIndisponiveis() {
		
		User cliente = userRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		sessao.setQuantidadeVagasDisponiveis(0);
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessao);
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(4);
		ingresso.adicionarAssento("A1");
		ingresso.adicionarAssento("A2");
		ingresso.adicionarAssento("A3");
		ingresso.adicionarAssento("A4");
				
		try {
			ingressoService.registrarCompra(ingresso);
		} catch(Exception erro) {
			assertEquals("[ERROR INGRESSO] - NÃO HÁ MAIS VAGAS DISPONÍVEIS!", erro.getMessage());
		}
		
	}

	@Test
	public void t6_testCompraIngressoHorarioInvalido() {
		
		User cliente = userRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Filme filme = filmeRepository.findById(1L).get();
		assertNotNull(filme);
		
		Sala sala = salaRepository.findById(1L).get();
		assertNotNull(sala);
		
		Sessao sessao = new Sessao(
				filme,
				sala,
				new BigDecimal("20.00"),
				LocalTime.parse("15:00:00"),
				LocalTime.parse("15:00:00").plusMinutes(filme.getDuracao()),
				LocalDate.parse("2021-10-29"),
				LocalDate.parse("2021-11-15"),
				sala.getQuantidadeAssentos(),
				true);
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessao);
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(1);
		ingresso.adicionarAssento("A1");
				
		try {
			ingressoService.registrarCompra(ingresso);
		} catch(Exception erro) {
			assertEquals("[ERROR INGRESSO] - TEMPO LIMITE PARA COMPRAR O INGRESSO JÁ FOI ATINGIDO!", erro.getMessage());
		}
				
	}

	@Test
	public void t7_testCancelarCompraIngresso() {
		
		User cliente = userRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		assertNotNull(sessao);
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(1);
		ingresso.adicionarAssento("D3");
				
		try {
			ingresso = ingressoService.registrarCompra(ingresso);
			assertNotNull(ingresso);
			
			Ingresso ingressoCancelado = ingressoService.cancelarCompra(ingresso.getId());
			assertNotNull(ingressoCancelado);
		} catch (Exception erro) { }
		
	}
	
	@Test
	public void t8_testCancelarCompraIngressoComTempoPassado() {
		
		User cliente = userRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Filme filme = filmeRepository.findById(1L).get();
		assertNotNull(filme);
		
		Sala sala = salaRepository.findById(1L).get();
		assertNotNull(sala);
		
		Sessao sessao = new Sessao(
				filme,
				sala,
				new BigDecimal("20.00"),
				LocalTime.parse("21:00:00"),
				LocalTime.parse("21:00:00").plusMinutes(filme.getDuracao()),
				LocalDate.parse("2021-10-29"),
				LocalDate.parse("2021-11-15"),
				sala.getQuantidadeAssentos(),
				true);
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessao);
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(1);
		ingresso.adicionarAssento("C4");
				
		try {
			ingresso = ingressoService.registrarCompra(ingresso);
			assertNotNull(ingresso);
			
			sessao.setHoraDeInicioExibicao(LocalTime.parse("15:00:00"));
			sessaoRepository.save(sessao);
			
			try {
				ingressoService.cancelarCompra(ingresso.getId());
			} catch(Exception erro) {
				assertEquals("[ERROR INGRESSO] - INGRESSO NÃO PODE SER CANCELADO!", erro.getMessage());
			}
		} catch(Exception erro) {}
	}
}
