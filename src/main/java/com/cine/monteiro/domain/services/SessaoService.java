package com.cine.monteiro.domain.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.events.SessaoCadastradaEvent;
import com.cine.monteiro.domain.model.cinema.Sala;
import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.repository.SessaoRepository;
import com.cine.monteiro.exception.SalaException;
import com.cine.monteiro.exception.SessaoException;

@Service
public class SessaoService {
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public Sessao cadastrar(Sessao sessao) throws SessaoException, SalaException {
		
		Sala sala = salaService.pesquisar(sessao.getSala().getId());

		// Validar horário da nova sessão
		for(Sessao sessaoCadastrada : sala.getSessoes()) {
			if(sessaoCadastrada.isAtiva()) {
				if(!(sessao.getHoraDeInicioExibicao().isAfter(sessaoCadastrada.getHoraDeTerminoExibicao())
				   || sessao.getHoraDeTerminoExibicao().isBefore(sessaoCadastrada.getHoraDeInicioExibicao()))) {
					throw new SessaoException("HORÁRIO NÃO DISPONÍVEL!");
				}
			}
		}
		
		eventPublisher.publishEvent(new SessaoCadastradaEvent(sessao));
		return sessaoRepository.save(sessao);
	}
	
	public Sessao interromperEmUmDia(Long id) throws SessaoException {
		Sessao sessao = validarDesativacao(id);
		sessao.setInterrompidaPorUmDia(true);
		return sessaoRepository.save(sessao);
	}
	
	public Sessao desativar(Long id) throws SessaoException {
		Sessao sessao = validarDesativacao(id);
		sessao.setInterrompida(true);
		return sessaoRepository.save(sessao);
	}
	
	public List<Sessao> listarDiaAtual() throws SessaoException {
		List<Sessao> sessoes = sessaoRepository.buscarSessoesDoDia(LocalTime.now());
		
		if(sessoes.isEmpty()) {
			throw new SessaoException("NÃO EXISTE SESSÕES CADASTRADAS PARA ESTE DIA!");
		}
		
		return sessoes;
	}
	
	public List<Sessao> listar() throws SessaoException {
		List<Sessao> sessoes = sessaoRepository.findAll();
		
		if(sessoes.isEmpty()) {
			throw new SessaoException("NÃO EXISTE SESSÕES CADASTRADAS!");
		}
		
		return sessoes;
	}
	
	public List<Sessao> listarPorSala(Long idSala) throws SessaoException, SalaException {
		Sala sala = salaService.pesquisar(idSala);
		List<Sessao> sessoes = sala.getSessoes();

		if(sessoes.isEmpty()) {
			throw new SessaoException("NÃO EXISTE SESSÕES CADASTRADAS!");
		}
		
		return sessoes;
		
	}
	
	private Sessao validarDesativacao(Long id) throws SessaoException {
		
		Sessao sessao = sessaoRepository.findById(id).get();
		
		if(sessao != null) {
			
			if(sessao.getAssentosReservados().size() > 0) {
				throw new SessaoException("A SESSÃO CONTÊM INGRESSOS VENDIDOS");
			}
			
			sessao.setAtiva(false);
			sessao.setDataResgistroInterrupcao(LocalDateTime.now());
		}
		return sessao;
	}
}
