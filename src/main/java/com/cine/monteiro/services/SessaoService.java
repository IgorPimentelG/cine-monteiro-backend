package com.cine.monteiro.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.exception.SalaException;
import com.cine.monteiro.exception.SessaoException;
import com.cine.monteiro.model.cinema.Sala;
import com.cine.monteiro.model.cinema.Sessao;
import com.cine.monteiro.repository.SessaoRepository;

@Service
public class SessaoService {
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private SalaService salaService;

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
		
		// TODO: registrar relatorio filme
		
		return sessaoRepository.save(sessao);		
	}
	
	public void interromperSessaoEmUmDia(Long id) throws SessaoException {
		Sessao sessao = sessaoRepository.findById(id).get();
		
		if(sessao != null) {
			
			if(sessao.getAssentosReservados().size() > 0) {
				throw new SessaoException("A SESSÃO CONTÊM INGRESSOS VENDIDOS");
			}
			
			sessao.setInterrompidaPorUmDia(true);
			sessao.setDataResgistroInterrupcao(LocalDateTime.now());
			
			sessaoRepository.save(sessao);
		}
	}
	
	public void desativar() {
		
	}
	
}
