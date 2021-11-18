package com.cine.monteiro.domain.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.enums.ClassificacaoEtaria;
import com.cine.monteiro.domain.events.*;
import com.cine.monteiro.domain.model.cinema.*;
import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.IngressoRepository;
import com.cine.monteiro.exception.*;
import com.cine.monteiro.utils.Promocional;

@Service
public class IngressoService {

	// Dependencias
	private IngressoRepository ingressoRepository;
	private SessaoService sessaoService;
	private FilmeService filmeService;
	private UserService userService;
	private ApplicationEventPublisher eventPublisher;
	private Promocional promocional;
	
	@Autowired
	public IngressoService(IngressoRepository ingressoRepository, SessaoService sessaoService, FilmeService filmeService, 
			UserService userService, ApplicationEventPublisher eventPublisher, Promocional promocional) {
		this.ingressoRepository = ingressoRepository;
		this.sessaoService = sessaoService;
		this.filmeService = filmeService;
		this.userService = userService;
		this.eventPublisher = eventPublisher;
		this.promocional = promocional;
	}
	
	public Ingresso registrarCompra(Ingresso ingresso) throws IngressoException, SessaoException, FilmeException, UserException {
		
		Sessao sessao = sessaoService.buscar(ingresso.getSessao().getId());
		Filme filme = filmeService.buscar(sessao.getFilme().getId());
		User cliente = userService.pesquisar(ingresso.getCliente().getId());
		
		int idadeCliente = calcularIdadeCliente(cliente.getDataNascimento());
				
		if(!sessao.isAtiva()) {
			throw new IngressoException("SESSÃO ESTÁ DESATIVADA!");
		} 
		
		// Verificar se o ingresso está sendo comprado 10 minutos antes de iniciar a exibição
		if(LocalTime.now().plusMinutes(10).isAfter(sessao.getHoraDeInicioExibicao())) {
			throw new IngressoException("TEMPO LIMITE PARA COMPRAR O INGRESSO JÁ FOI ATINGIDO!");
		} 
		
		// Verificar se existe assentos disponíveis
		if(sessao.getQuantidadeVagasDisponiveis() < ingresso.getQuantidade()) {	
			throw new IngressoException("NÃO HÁ MAIS VAGAS DISPONÍVEIS!");
		}
		
		// Verificar idade do cliente 
		if(filme.getClassificacaoEtaria() != ClassificacaoEtaria.LIVRE) {
			int classificacaoEtaria = converterClassifcacaoEtaria(filme.getClassificacaoEtaria());
			
			if(idadeCliente < classificacaoEtaria) {
				throw new IngressoException("CLASSIFICAÇÃO ETÁRIA IMPRÓPRIA");
			}
		}
		
		// Verificar assentos reservados
		if(ingresso.getAssentosReservados().size() != ingresso.getQuantidade()) {
			throw new IngressoException("QUANTIDADE DE ASSENTOS RESERVADOS NÃO É A MESMA QUANTIDADE DE INGRESSOS COMPRADOS!");
		}
		
		for(String assento : ingresso.getAssentosReservados()) {
			if(sessao.getAssentosReservados().contains(assento)) {
				throw new IngressoException("ASSENTO(S) JÁ RESERVADO(S)!");
			}
		}
		
		ingresso.setValorUnitario(sessao.getPrecoIngresso());
		BigDecimal valorTotal = ingresso.getValorUnitario().multiply(new BigDecimal(ingresso.getQuantidade()));
		BigDecimal desconto = ((valorTotal.multiply(new BigDecimal(descontoPromocional()))).divide(new BigDecimal(100)));
		valorTotal = valorTotal.subtract(desconto);
		ingresso.setValorTotal(valorTotal);
		
		eventPublisher.publishEvent(new IngressoEmitidoEvent(ingresso));
		return ingressoRepository.save(ingresso);
	}
	
	public Ingresso cancelarCompra(Long id) throws IngressoException {
		
		Ingresso ingresso = buscar(id);
		
		Sessao sessao = ingresso.getSessao();
		
		if(LocalTime.now().isBefore(sessao.getHoraDeInicioExibicao())) {
			ingressoRepository.delete(ingresso);
			eventPublisher.publishEvent(new IngressoCanceladoEvent(ingresso));
			return ingresso;
		}
		
		throw new IngressoException("INGRESSO NÃO PODE SER CANCELADO!");
		
	}
	
	public Ingresso buscar(Long id) throws IngressoException {
		Ingresso ingresso = ingressoRepository.findById(id).get();
		validarRetorno(ingresso);
		return ingresso;
	}
	
	private Integer descontoPromocional() {
		return promocional.desconto("CINE10");
	}
	
	private void validarRetorno(Ingresso ingresso) throws IngressoException {
		if(ingresso == null) {
			throw new IngressoException("INGRESSO NÃO ENCONTRADO!");
		}
	}
	
	private int calcularIdadeCliente(LocalDate dataNascimento) {
		LocalDate dataAtual = LocalDate.now();
		Period periodo = Period.between(dataNascimento, dataAtual);
		return periodo.getYears();
	}
	
	private int converterClassifcacaoEtaria(ClassificacaoEtaria classificacaoEtaria) {
		switch(classificacaoEtaria) {
			case MAIOR_OU_IGUAL_10:
				return 10;
			case MAIOR_OU_IGUAL_12:
				return 12;
			case MAIOR_OU_IGUAL_14:
				return 14;
			case MAIOR_OU_IGUAL_16:
				return 16;
			case MAIOR_OU_IGUAL_18:
				return 18;
			default:
				return 0;
		}
	}

}
