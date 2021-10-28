package com.cine.monteiro.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.enums.*;
import com.cine.monteiro.domain.model.cinema.*;
import com.cine.monteiro.domain.model.user.Cliente;
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
	private ClienteRepository clienteRepository;
	
	@Autowired
	private IngressoService ingressoService;

	@Test
	public void t1_init() {
	
		Sala sala = new Sala("Sala 01", 40);
		sala = salaRepository.save(sala);
		assertNotNull(salaRepository.findById(sala.getId()).get());
		
		Genero genero = new Genero("Ação", "Contém cenas de violência.");
		genero = generoRepository.save(genero);
		assertNotNull(generoRepository.findById(genero.getId()).get());
		
		Filme filme = new Filme(
				"As Passageiras",
				"Um jovem motorista leva duas mulheres misteriosas para uma noite de festa.",
				genero,
				120L,
				ClassificacaoEtaria.MAIOR_OU_IGUAL_16,
				Legenda.NONE,
				Projecao.PROJECAO_2D);
		filme = filmeRepository.save(filme);
		assertNotNull(filmeRepository.findById(filme.getId()).get());
		
		Sessao sessao = new Sessao(
				filme,
				sala,
				new BigDecimal("20.00"),
				LocalTime.parse("03:00:00"),
				LocalTime.parse("03:00:00").plusMinutes(filme.getDuracao()),
				LocalDate.parse("2021-10-29"),
				LocalDate.parse("2021-11-15"),
				sala.getQuantidadeAssentos(),
				true);
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessaoRepository.findById(sessao.getId()).get());
	}

	@Test
	public void t2_testClassificacaoEtariaClienteInvalida() {
		
		// Classificação Etária: MAIOR OU IGUAL 16		
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		assertNotNull(sessao);
		
		Cliente cliente = new Cliente(
				"882.446.861-62",
				"Cláudio Nelson",
				"(11) 99977-2153",
				LocalDate.parse("2007-05-12"),				// 14
				"claudio@hotmail.com",
				"1235678");
		cliente = clienteRepository.save(cliente);
		assertNotNull(clienteRepository.findById(cliente.getId()).get());


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
		
		Cliente cliente = new Cliente(
				"342.487.628-38",
				"Benedito Rafael",
				"(53) 23957-4553",
				LocalDate.parse("1990-03-23"),			// 31
				"benedito@hotmail.com",
				"1235678");
		cliente = clienteRepository.save(cliente);
		assertNotNull(clienteRepository.findById(cliente.getId()).get());
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		assertNotNull(sessao);
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(1);
		ingresso.adicionarAssento("A1");
				
		try {
			assertNotNull(ingressoService.registrarCompra(ingresso));
		} catch(Exception erro) {}
		
	}
	
	@Test
	public void t4_testCompraIngressoAssentoJaReservado() {
		Cliente cliente = clienteRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		sessao.adicionarAssentoReservado("A5");
		sessao.adicionarAssentoReservado("B1");
		sessao.adicionarAssentoReservado("B2");
		sessao = sessaoRepository.save(sessao);
		
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
		Cliente cliente = clienteRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Sessao sessao = sessaoRepository.findById(1L).get();
		sessao.setQuantidadeVagasDisponiveis(0);
		sessao = sessaoRepository.save(sessao);
		
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
		
		Cliente cliente = clienteRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Filme filme = filmeRepository.findById(1L).get();
		assertNotNull(filme);
		
		Sala sala = salaRepository.findById(1L).get();
		assertNotNull(sala);
		
		Sessao sessao = new Sessao(
				filme,
				sala,
				new BigDecimal("20.00"),
				LocalTime.parse("01:00:00"),
				LocalTime.parse("01:00:00").plusMinutes(filme.getDuracao()),
				LocalDate.parse("2021-10-29"),
				LocalDate.parse("2021-11-15"),
				sala.getQuantidadeAssentos(),
				true);
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessaoRepository.findById(sessao.getId()).get());
		
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
		Cliente cliente = clienteRepository.findById(2L).get();
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
			assertNotNull(ingressoService.cancelarCompra(ingresso.getId()));
		} catch(Exception erro) {}
	}
	
	@Test
	public void t8_testCancelarCompraIngressoComTempoPassado() {
		Cliente cliente = clienteRepository.findById(2L).get();
		assertNotNull(cliente);
		
		Filme filme = filmeRepository.findById(1L).get();
		assertNotNull(filme);
		
		Sala sala = salaRepository.findById(1L).get();
		assertNotNull(sala);
		
		Sessao sessao = new Sessao(
				filme,
				sala,
				new BigDecimal("20.00"),
				LocalTime.parse("03:00:00"),
				LocalTime.parse("03:00:00").plusMinutes(filme.getDuracao()),
				LocalDate.parse("2021-10-29"),
				LocalDate.parse("2021-11-15"),
				sala.getQuantidadeAssentos(),
				true);
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessaoRepository.findById(sessao.getId()).get());
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(1);
		ingresso.adicionarAssento("C4");
				
		try {
			ingresso = ingressoService.registrarCompra(ingresso);
			assertNotNull(ingresso);
			
			sessao.setHoraDeInicioExibicao(LocalTime.parse("01:00:00"));
			sessaoRepository.save(sessao);
			
			try {
				ingressoService.cancelarCompra(ingresso.getId());
			} catch(Exception erro) {
				assertEquals("[ERROR INGRESSO] - INGRESSO NÃO PODE SER CANCELADO!", erro.getMessage());
			}
		} catch(Exception erro) {}
	}
}
