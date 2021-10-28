package com.cine.monteiro.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.enums.ClassificacaoEtaria;
import com.cine.monteiro.domain.enums.Legenda;
import com.cine.monteiro.domain.enums.Projecao;
import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.model.cinema.Genero;
import com.cine.monteiro.domain.model.cinema.Sala;
import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.repository.FilmeRepository;
import com.cine.monteiro.domain.repository.GeneroRepository;
import com.cine.monteiro.domain.repository.SessaoRepository;
import com.cine.monteiro.domain.services.SalaService;

@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalaTest {
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private FilmeRepository filmeRepository;
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private SalaService salaService;
	
	@Test
	public void t1_testCadadastrarSalaQuantidadeAssentosInvalidos() {
		
		/*
		 * MIN: 15
		 * MAX: 40
		 */
		
		String mensagemError = "[SALA ERROR] - SALA NÃO CONTÊM A QUANTIDADE DE ASSENTOS NECESSÁRIOS PARA SER CADASTRADA!";
		
		try {
			Sala sala = new Sala("Sala 01", 41);
			salaService.salvar(sala);
		} catch(Exception erro) {
			assertEquals(mensagemError, erro.getMessage());
		}
		
		try {
			Sala sala = new Sala("Sala 02", 14);
			salaService.salvar(sala);
		} catch(Exception erro) {
			assertEquals(mensagemError, erro.getMessage());
		}

	}

	@Test
	public void t2_testDeletarSalaComSessoesAtivas() {
		try {
			Sala sala = new Sala("Sala 01", 40);
			sala = salaService.salvar(sala);
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
					LocalTime.parse("15:00:00"),
					LocalTime.parse("15:00:00").plusMinutes(filme.getDuracao()),
					LocalDate.parse("2021-10-29"),
					LocalDate.parse("2021-11-15"),
					sala.getQuantidadeAssentos(),
					true);
			sessao = sessaoRepository.save(sessao);
			sala.adicionarSessao(sessao);
			assertNotNull(sessao);
			
			salaService.deletar(sala.getId());
					
		} catch(Exception erro) { 
			assertEquals("[SALA ERROR] - A SALA CONTÊM SESSÕES ATIVAS!", erro.getMessage());
		}
	}
}
