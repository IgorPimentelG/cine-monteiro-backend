package com.cine.monteiro.domain.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.model.cinema.*;
import com.cine.monteiro.domain.repository.SalaRepository;
import com.cine.monteiro.domain.repository.SessaoRepository;
import com.cine.monteiro.exception.*;


@Service
public class SessaoService {
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private SalaRepository salaRepository;
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private FilmeService filmeService;
	
	//@Autowired
	//private ApplicationEventPublisher eventPublisher;

	public void cadastrar(Sessao sessao) throws SessaoException, SalaException, FilmeException {
		
		Sala sala = salaService.pesquisar(sessao.getSala().getId());
		Filme filme = filmeService.buscar(sessao.getFilme().getId());
		
		sessao.setHoraDeTerminoExibicao(sessao.getHoraDeInicioExibicao().plusMinutes(filme.getDuracao()));
		sessao.setQuantidadeVagasDisponiveis(sala.getQuantidadeAssentos());
	
		if(sessao.getInicioPeriodoExibicao().isAfter(sessao.getTerminoPeriodoExibicao()) || 
				sessao.getInicioPeriodoExibicao().isBefore(LocalDate.now())) {
			throw new SessaoException("PERÍODO DE EXIBIÇÃO INVÁLIDO!");
		}
	
		// Validar horário da nova sessão
		if(sessao.getHoraDeTerminoExibicao().isAfter(LocalTime.parse("00:00:00")) &&
				sessao.getHoraDeTerminoExibicao().isBefore(LocalTime.parse("07:00:00"))) {
			throw new SessaoException("HORÁRIO NÃO DISPONÍVEL!");
		}
		
		if(sala.getSessoes().size() > 0) {
			for(Sessao sessaoCadastrada : sala.getSessoes()) {
				if(sessaoCadastrada.isAtiva() || sessaoCadastrada.isInterrompidaPorUmDia()) {
					if(!(sessao.getHoraDeInicioExibicao().isAfter(sessaoCadastrada.getHoraDeTerminoExibicao())
					   || sessao.getHoraDeTerminoExibicao().isBefore(sessaoCadastrada.getHoraDeInicioExibicao()))) {
						throw new SessaoException("HORÁRIO INDISPONÍVEL!");
					}
				}
			}
		}
		
		if(sessao.getInicioPeriodoExibicao().isEqual(LocalDate.now())) {
			sessao.setAtiva(true);
		}

		//eventPublisher.publishEvent(new SessaoCadastradaEvent(sessao));
		sessaoRepository.save(sessao);
		sala.adicionarSessao(sessao);
		salaRepository.save(sala);
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
	
	public Sessao buscar(Long id) throws SessaoException {
		Sessao sessao = sessaoRepository.findById(id).get();
		
		if(sessao == null) {
			throw new SessaoException("SESSÃO NÃO CADASTRADA!");
		}
		
		return sessao;
	}

	public Sessao update(Sessao sessao) { 
		Sessao sessaoAntiga = sessaoRepository.findById(sessao.getId()).get();
		BeanUtils.copyProperties(sessao, sessaoAntiga, "id");
		return sessaoRepository.save(sessao);
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
