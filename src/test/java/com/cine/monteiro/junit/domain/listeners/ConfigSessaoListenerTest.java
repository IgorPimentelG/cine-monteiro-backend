package com.cine.monteiro.junit.domain.listeners;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.enums.*;
import com.cine.monteiro.domain.events.IngressoEmitidoEvent;
import com.cine.monteiro.domain.listeners.ConfigSessaoListener;
import com.cine.monteiro.domain.model.cinema.*;
import com.cine.monteiro.domain.model.user.Profile;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ConfigSessaoListenerTest {
	
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
	private IngressoRepository ingressoRepository;
	
	@Autowired
	private ConfigSessaoListener configSessaoListener;

	@Test
	public void t1_testAssentosReservadosIngressoAdicionadoNaSessao() {
		
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
				new BigDecimal("20.00"),
				LocalTime.parse("21:00:00"),
				LocalTime.parse("21:00:00").plusMinutes(filme.getDuracao()),
				LocalDate.parse("2021-10-29"),
				LocalDate.parse("2021-11-15"),										
				sala.getQuantidadeAssentos(),
				true);																
		sessao = sessaoRepository.save(sessao);
		assertNotNull(sessao);
		
		User cliente = new User(
				"342.487.628-38",
				"Benedito Rafael",
				"(53) 23957-4553",
				LocalDate.parse("1990-03-23"),			
				"cinemonteiro.ads@gmail.com",
				"1235678", new ArrayList<Profile>());
		cliente = userRepository.save(cliente);
		assertNotNull(cliente);
		
		Ingresso ingresso = new Ingresso();
		ingresso.setCliente(cliente);
		ingresso.setSessao(sessao);
		ingresso.setQuantidade(2);
		ingresso.adicionarAssento("A1");
		ingresso.adicionarAssento("A2");
		ingresso = ingressoRepository.save(ingresso);
		assertNotNull(ingresso);
		
		assertEquals(0, sessao.getAssentosReservados().size());
		
		IngressoEmitidoEvent event = new IngressoEmitidoEvent(ingresso);
		assertDoesNotThrow(() -> configSessaoListener.reconfigurarSessaoIngressoComprado(event));
		
		sessao = sessaoRepository.findById(sessao.getId()).get();
		assertEquals(2, sessao.getAssentosReservados().size());
	}

}