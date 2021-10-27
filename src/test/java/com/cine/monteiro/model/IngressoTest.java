package com.cine.monteiro.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.enums.ClassificacaoEtaria;
import com.cine.monteiro.domain.enums.Legenda;
import com.cine.monteiro.domain.enums.Projecao;
import com.cine.monteiro.domain.model.cinema.*;
import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.domain.repository.*;
import com.cine.monteiro.domain.services.IngressoService;
import com.cine.monteiro.exception.IngressoException;


@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
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
	
	// Models
	private Sala sala;
	private Filme filme;
	private Sessao sessao;

	@Before
	public void init() {
		
		sala = new Sala("Sala 01", 40);
		salaRepository.save(sala);
		assertNotNull(salaRepository.findById(1L).get());
		
		Genero genero = new Genero("Ação", "Contém cenas de violência.");
		generoRepository.save(genero);
		assertNotNull(generoRepository.findById(1L).get());
		
		filme = new Filme(
				"As Passageiras",
				"Um jovem motorista leva duas mulheres misteriosas para uma noite de festa.",
				genero,
				120L,
				ClassificacaoEtaria.MAIOR_OU_IGUAL_16,
				Legenda.NONE,
				Projecao.PROJECAO_2D);
		filmeRepository.save(filme);
		assertNotNull(filmeRepository.findById(1L).get());
		
		sessao = new Sessao(
				filme,
				sala,
				new BigDecimal("20.00"),
				LocalTime.parse("19:00:00"),
				LocalTime.parse("19:00:00").plusMinutes(filme.getDuracao()),
				LocalDate.parse("2021-10-28"),
				LocalDate.parse("2021-11-15"),
				sala.getQuantidadeAssentos(),
				true);
		sessaoRepository.save(sessao);
		assertNotNull(sessaoRepository.findById(1L).get());
	}
	
	@Test
	@DisplayName("Teste Classificação Etária do Cliente Inválida!")
	public void testClassificacaoEtariaClienteInvalida() {
		
		// Classificação Etária: MAIOR OU IGUAL 16
		 
		Cliente cliente = new Cliente(
				"882.446.861-62",
				"Cláudio Nelson",
				"(11) 99977-2153",
				LocalDate.parse("2007-05-12"),
				"claudio@hotmail.com",
				"1235678");
		clienteRepository.save(cliente);
		assertNotNull(clienteRepository.findById(1L).get());
		
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
	

}
