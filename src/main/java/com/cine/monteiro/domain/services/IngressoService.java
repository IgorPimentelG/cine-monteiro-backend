package com.cine.monteiro.domain.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.enums.ClassificacaoEtaria;
import com.cine.monteiro.domain.events.*;
import com.cine.monteiro.domain.model.cinema.*;
import com.cine.monteiro.domain.model.user.Cliente;
import com.cine.monteiro.domain.repository.IngressoRepository;
import com.cine.monteiro.exception.*;

@Service
public class IngressoService {

	@Autowired
	private IngressoRepository ingressoRepository;
	
	@Autowired
	private SessaoService sessaoService;
	
	@Autowired
	private FilmeService filmeService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public Ingresso registrarCompra(Ingresso ingresso) throws IngressoException, SessaoException, FilmeException, UserException {
		
		Sessao sessao = sessaoService.buscar(ingresso.getSessao().getId());
		Filme filme = filmeService.buscar(sessao.getFilme().getId());
		Cliente cliente = clienteService.pesquisar(ingresso.getCliente().getId());
		
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
				throw new IngressoException("ASSENTOS JÁ RESERVADOS!");
			}
		}
		
		
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
