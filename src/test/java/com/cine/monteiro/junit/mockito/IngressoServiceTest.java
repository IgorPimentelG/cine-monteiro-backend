package com.cine.monteiro.junit.mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.cine.monteiro.domain.enums.*;
import com.cine.monteiro.domain.events.*;
import com.cine.monteiro.domain.listeners.ConfigSessaoListener;
import com.cine.monteiro.domain.model.cinema.*;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.IngressoRepository;
import com.cine.monteiro.domain.services.*;
import com.cine.monteiro.exception.IngressoException;
import com.cine.monteiro.utils.Promocional;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IngressoServiceTest {
	
	// Mocks
	@Mock private SessaoService sessaoServiceMock;
	@Mock private FilmeService filmeServiceMock;
	@Mock private UserService userServiceMock;
	@Mock private Promocional promocionalMock;
	@Mock private IngressoRepository ingressoRepositoryMock;
	@Mock private ApplicationEventPublisher eventPublisherMock;
	
	private IngressoService ingressoService;
	
	@Before
	public void setup() {
		ingressoService = new IngressoService(
				ingressoRepositoryMock,
				sessaoServiceMock, 
				filmeServiceMock, 
				userServiceMock, 
				eventPublisherMock,
				promocionalMock);
	}
	
    @Test
    public void t1_cancelarCompraIngressoSucess() throws Exception {
    	
    	Ingresso ingressoMock = mock(Ingresso.class);
    	Sessao sessaoMock = mock(Sessao.class);
    	
    	when(sessaoMock.getHoraDeInicioExibicao()).thenReturn(LocalTime.parse("15:00:00"));
    	when(ingressoMock.getId()).thenReturn(1L);
    	when(ingressoMock.getSessao()).thenReturn(sessaoMock);   	
    	when(ingressoRepositoryMock.findById(1L)).thenReturn(Optional.of(ingressoMock));
    	
    	Ingresso ingressoCancelado = ingressoService.cancelarCompra(1L);
    	
    	verify(ingressoRepositoryMock, times(1)).delete(ingressoMock);
    	verify(eventPublisherMock, times(1)).publishEvent(any(IngressoCanceladoEvent.class));
    	
    	assertNotNull(ingressoCancelado);
    	assertEquals(ingressoMock, ingressoCancelado);
    }
    
    @Test
    public void t2_cancelarCompraIngressoError() throws Exception {
    	
    	Ingresso ingressoMock = mock(Ingresso.class);
    	Sessao sessaoMock = mock(Sessao.class);
    	
    	when(sessaoMock.getHoraDeInicioExibicao()).thenReturn(LocalTime.parse("00:30:00"));
    	when(ingressoMock.getId()).thenReturn(1L);
    	when(ingressoMock.getSessao()).thenReturn(sessaoMock);   	
    	when(ingressoRepositoryMock.findById(1L)).thenReturn(Optional.of(ingressoMock));
    	
    	IngressoException ingressoException = assertThrows(IngressoException.class, () -> ingressoService.cancelarCompra(1L));
    	
    	assertEquals("[ERROR INGRESSO] - INGRESSO NÃO PODE SER CANCELADO!", ingressoException.getMessage());
    	
    	verify(ingressoRepositoryMock, never()).delete(ingressoMock);
    	verify(eventPublisherMock, never()).publishEvent(any(IngressoCanceladoEvent.class));
    }
    
    @Test
    public void t3_aplicacaoDescontoPromocionalSucess() throws Exception {
    	
    	User cliente = factoryUserCliente();
    	Filme filme = factoryFilme();
    	Sessao sessao = factorySessao();
    	Ingresso ingresso = factoryIngresso();
    	
    	when(promocionalMock.gerarCupom(cliente)).thenReturn("CINE10");
    	when(promocionalMock.validarCupom("CINE10")).thenReturn(true);
    	when(promocionalMock.desconto(promocionalMock.gerarCupom(cliente))).thenReturn(10);
    	
    	when(sessaoServiceMock.buscar(anyLong())).thenReturn(sessao);
    	when(filmeServiceMock.buscar(anyLong())).thenReturn(filme);
    	when(userServiceMock.pesquisar(anyLong())).thenReturn(cliente);
    	when(ingressoRepositoryMock.save(any(Ingresso.class))).thenReturn(ingresso);
    	
    	Ingresso ingressoComprado = ingressoService.registrarCompra(ingresso);
    	
    	verify(promocionalMock, times(1)).desconto(any(String.class));
    	verify(eventPublisherMock, times(1)).publishEvent(any(IngressoEmitidoEvent.class));
    	verify(ingressoRepositoryMock, times(1)).save(any(Ingresso.class));
    	
    	assertNotNull(ingressoComprado);
    	
    	/*
    	 * Cálculo Desconto
    	 * (40 * 10) / 100 = 4
    	 *  40 - 4 = 36
    	 */
    	assertEquals(new BigDecimal("36.00"), ingressoComprado.getValorTotal());
    }
    
    @Test
    public void t4_configSessaoPosCompraIngressoSucess() throws Exception {
    	
    	ConfigSessaoListener configSessaoListaner = new ConfigSessaoListener(sessaoServiceMock);
    	
    	Ingresso ingressoMock = mock(Ingresso.class);
    	Sessao sessaoMock = mock(Sessao.class);
    	IngressoEmitidoEvent eventMock = mock(IngressoEmitidoEvent.class);
    		
    	when(eventMock.getIngresso()).thenReturn(ingressoMock);
    	when(ingressoMock.getSessao()).thenReturn(sessaoMock);
    	when(ingressoMock.getQuantidade()).thenReturn(10);
    	when(ingressoMock.getAssentosReservados()).thenReturn(factoryAssentosReservados());
    	when(sessaoMock.getQuantidadeVagasDisponiveis()).thenReturn(10);
    	when(sessaoMock.getId()).thenReturn(1L);
    	when(sessaoServiceMock.buscar(anyLong())).thenReturn(sessaoMock);
    	
    	configSessaoListaner.reconfigurarSessaoIngressoComprado(eventMock);
    	
    	verify(sessaoMock, times(10)).adicionarAssentoReservado(anyString());
    	verify(sessaoServiceMock, times(1)).update(any(Sessao.class));
    }
    
    
    // Dados
    private User factoryUserCliente() {
    	User cliente = new User(
				"882.446.861-62",
				"Cláudio Nelson",
				"(11) 99977-2153",
				LocalDate.parse("1990-05-12"),
				"claudio@cinemonteiro.com",
				"1235678", 
				"CLIENT");
    	
    	cliente.setId(1L);
    	return cliente;
    }
    
    private Sala factorySala() {
    	Sala sala = new Sala("Sala 01", 40);
    	sala.setId(1L);
    	return sala;
    }
    
    private Filme factoryFilme() {
		Filme filme = new Filme(
				"As Passageiras",
				"Um jovem motorista leva duas mulheres misteriosas para uma noite de festa.",
				new Genero("Ação", null),
				120L,
				ClassificacaoEtaria.MAIOR_OU_IGUAL_16,
				Legenda.NONE,
				Projecao.PROJECAO_2D);
		
		filme.setId(1L);
		return filme;
    }
    
    private Sessao factorySessao() {
    	Sessao sessao = new Sessao(
				factoryFilme(),
				factorySala(),
				new BigDecimal("20.00"),												// Preço
				LocalTime.parse("21:00:00"),											// Hora de Início
				LocalTime.parse("21:00:00").plusMinutes(factoryFilme().getDuracao()),	// Hora de Término
				LocalDate.parse("2021-11-29"),											// Data de Início
				LocalDate.parse("2021-11-15"),											// Data de Final
				factorySala().getQuantidadeAssentos(),
				true);	
    	
    	sessao.setId(1L);
    	return sessao;
    }
    
    private Ingresso factoryIngresso() {
    	Ingresso ingresso = new Ingresso();
    	ingresso.setId(1L);
    	ingresso.setCliente(factoryUserCliente());
    	ingresso.setSessao(factorySessao());
    	ingresso.setQuantidade(2);
    	ingresso.adicionarAssento("A1");
    	ingresso.adicionarAssento("A2");

    	return ingresso;
    }
    
    private Set<String> factoryAssentosReservados() {
    	Set<String> assentosReservados = new HashSet<String>();
    	assentosReservados.add("A1");
    	assentosReservados.add("A2");
    	assentosReservados.add("A3");
    	assentosReservados.add("A4");
    	assentosReservados.add("A5");
    	assentosReservados.add("B1");
    	assentosReservados.add("B2");
    	assentosReservados.add("B3");
    	assentosReservados.add("B4");
    	assentosReservados.add("B5");
    	
    	return assentosReservados;
    }

}
