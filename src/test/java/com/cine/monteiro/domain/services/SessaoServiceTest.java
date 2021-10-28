package com.cine.monteiro.domain.services;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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



@SpringBootTest
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
public class SessaoServiceTest {
	
	@Autowired
	private SessaoService sessaoService;
	@Autowired
	private FilmeService filmeService;
	@Autowired
	private SalaService salaService;
	@Autowired
	private GeneroService generoService;

	private static Sessao sessao;
	private static Filme filme;
	private static Genero genero;
	
	@BeforeAll
	public static void init() {
		genero = new Genero();
		genero.setDescricao("sem descrição");
		genero.setGenero("Fantasia");
	}
	
	@BeforeEach
	public void setup() {
		generoService.salvar(genero);
		assertNotNull(generoService.buscar(1L));
		
		filme = new Filme();
		filme.setTitulo("Homem Aranha");
		filme.setSinopse("confirma retorno de mais vilões de filmes com Tobey Maguire e Andrew Garfield");
		filme.setGenero(genero);
		filme.setDuracao(40L);
		filme.setClassificacaoEtaria(ClassificacaoEtaria.MAIOR_OU_IGUAL_10);
		filme.setLegenda(Legenda.PORTUGUES);
		filme.setProjecao(Projecao.PROJECAO_2D);
		
		assertDoesNotThrow(() -> filmeService.salvar(filme));
		
		Sala sala = new Sala();
		sala.setNome("sala 01");
		sala.setQuantidadeAssentos(40);
		sala.adicionarSessao(sessao);
		
		assertDoesNotThrow(() -> salaService.salvar(sala));
		
		sessao = new Sessao();
		sessao.setSala(sala);
		sessao.setFilme(filme);
		sessao.setPrecoIngresso(new BigDecimal(100.50));
		sessao.setHoraDeInicioExibicao(LocalTime.parse("23:00:00"));
		//sessao.setHoraDeTerminoExibicao(LocalTime.parse("23:40:00"));
		
		//datas futuras
		sessao.setInicioPeriodoExibicao(LocalDate.parse("2021-10-29"));
		
		sessao.setTerminoPeriodoExibicao(LocalDate.parse("2021-11-15"));
		//sessao.setDataResgistroInterrupcao(LocalDateTime.now());
		sessao.setQuantidadeVagasDisponiveis(40);
		//sessao.setAtiva(true);
		sessao.setInterrompida(true);
		sessao.setInterrompidaPorUmDia(false);
		HashSet<String> numAssentos = new HashSet<String>();
		numAssentos.add("10");
		sessao.setAssentosReservados(numAssentos);
	}

	@Test
	public void testCadastrar() {
		assertDoesNotThrow(() -> sessaoService.cadastrar(sessao));
	
	}
}
