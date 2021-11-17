package com.cine.monteiro.junit.mockito;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import com.cine.monteiro.domain.model.cinema.Filme;
import com.cine.monteiro.domain.model.cinema.Sala;
import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.repository.SalaRepository;
import com.cine.monteiro.domain.repository.SessaoRepository;
import com.cine.monteiro.domain.services.FilmeService;
import com.cine.monteiro.domain.services.SalaService;
import com.cine.monteiro.domain.services.SessaoService;
import com.cine.monteiro.exception.SessaoException;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("unchecked")
public class SessaoServiceMockitoTest {
	
	@Mock //@Mock simula ou cria uma cópia da estrutura da classe SessaoRepository.
	private SessaoRepository sessaoRepository;

	@Mock
	private SalaRepository salaRepository;
	
	@InjectMocks //cria uma instância e injeta os objetos @Mock.
	private SessaoService sessaoService;
	
	private SalaService salaService = mock(SalaService.class);

	private FilmeService filmeService = mock(FilmeService.class);
	
	//cadastrar
	@Test
	public void t1_test() throws Exception {
		
		Sala sala = new Sala();
		sala.setId(1L);
		
		Filme filme = new Filme();
		filme.setId(1L);
		filme.setDuracao(40L);
		
		//quando...
		when(salaService.pesquisar(eq(1L))).thenReturn(sala);
		when(filmeService.buscar(eq(1L))).thenReturn(filme);
		
		Sessao sessao = new Sessao();
		sessao.setInicioPeriodoExibicao(LocalDate.parse("2021-11-15"));
		sessao.setTerminoPeriodoExibicao(LocalDate.parse("2021-11-29"));
		sessao.setHoraDeInicioExibicao(LocalTime.parse("23:00:00"));
		sessao.setSala(sala);
		sessao.setFilme(filme);
		sala.adicionarSessao(sessao);
		
		SessaoException sessaoException1 = assertThrows(SessaoException.class, () -> sessaoService.cadastrar(sessao));
		assertEquals("[ERROR SESSÃO] - PERÍODO DE EXIBIÇÃO INVÁLIDO!", sessaoException1.getMessage());
		
		//alteração 1
		sessao.setInicioPeriodoExibicao(LocalDate.now());
		sessao.setHoraDeInicioExibicao(LocalTime.parse("23:30:00"));
		 
		SessaoException sessaoException2 = assertThrows(SessaoException.class, () -> sessaoService.cadastrar(sessao));  
		assertEquals("[ERROR SESSÃO] - HORÁRIO NÃO DISPONÍVEL!", sessaoException2.getMessage());
		
		//alteração 2
		sessao.setHoraDeInicioExibicao(LocalTime.parse("23:00:00"));
		sessao.setInterrompidaPorUmDia(true);
		sessao.setHoraDeTerminoExibicao(LocalTime.parse("22:00:00"));
		
		SessaoException sessaoException3 = assertThrows(SessaoException.class, () -> sessaoService.cadastrar(sessao));
		assertEquals("[ERROR SESSÃO] - HORÁRIO INDISPONÍVEL!", sessaoException3.getMessage());
		
		//alteração 3
		sessao.setInterrompidaPorUmDia(false);
		sessao.setHoraDeTerminoExibicao(LocalTime.parse("23:40:00"));
				
		assertDoesNotThrow(() -> sessaoService.cadastrar(sessao));
		
		verify(salaService, times(4)).pesquisar(1L);
		verify(filmeService, times(4)).buscar(1L);
	}
	
	//buscarSemException
	@Test
	public void t2_test() {
		Sessao name = new Sessao();
		name.setId(2L);
		Optional<Sessao> sessao = Optional.of(name);
		
		when(sessaoRepository.findById(2L)).thenReturn(sessao);
		assertDoesNotThrow(() -> sessaoService.buscar(2L));
		
		verify(sessaoRepository, times(1)).findById(2L);
	}
	
	//buscarException
	@Test
	public void t3_test() throws Exception {
		SessaoService mockServiceSessao = mock(SessaoService.class);
		doThrow(new SessaoException("SESSÃO NÃO CADASTRADA!")).when(mockServiceSessao).buscar(anyLong());
		
		SessaoException exception = assertThrows(SessaoException.class, () -> mockServiceSessao.buscar(anyLong()));
		assertEquals("[ERROR SESSÃO] - SESSÃO NÃO CADASTRADA!", exception.getMessage());
		
		verify(mockServiceSessao, times(1)).buscar(anyLong());
	}
	
	//listarDiaAtualException 
	@Test
	public void t4_test() throws Exception {
		
		SessaoService mockServiceSessao = mock(SessaoService.class);
		doThrow(new SessaoException("NÃO EXISTE SESSÕES CADASTRADAS PARA ESTE DIA!")).when(mockServiceSessao).listarDiaAtual();
		
		SessaoException sessaoException = assertThrows(SessaoException.class, () -> mockServiceSessao.listarDiaAtual());
		assertEquals("[ERROR SESSÃO] - NÃO EXISTE SESSÕES CADASTRADAS PARA ESTE DIA!", sessaoException.getMessage());
		
		verify(mockServiceSessao, times(1)).listarDiaAtual();
	}
	
	//listarDiaAtualSemException
	@Test
	public void t5_test() throws Exception {
		
		Sessao sessao = mock(Sessao.class);
		when(sessao.isAtiva()).thenReturn(true);
		when(sessao.getHoraDeInicioExibicao()).thenReturn(LocalTime.now());
		
		LinkedList<Sessao> mockSessoes = mock(LinkedList.class); 
		when(mockSessoes.get(0)).thenReturn(sessao);
		
		assertEquals(sessao, mockSessoes.get(0));
		verify(mockSessoes, times(1)).get(0);
		
		SessaoService mockServiceSessao = mock(SessaoService.class); 
		when(mockServiceSessao.listarDiaAtual()).thenReturn(mockSessoes);

		assertDoesNotThrow(() -> mockServiceSessao.listarDiaAtual());
		
		verify(mockServiceSessao, times(1)).listarDiaAtual();
	}
	
}
