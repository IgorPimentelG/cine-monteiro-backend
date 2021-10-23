package com.cine.monteiro.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.monteiro.domain.model.cinema.Sessao;
import com.cine.monteiro.domain.model.relatorio.SessaoRelatorio;
import com.cine.monteiro.domain.repository.SessaoRelatorioRepository;

@Service
public class SessaoRelatorioService {
	
	@Autowired
	private SessaoRelatorioRepository sessaoRelatorioRepository;
	
	public void registrarRelatorio(Sessao sessao) {
		SessaoRelatorio sessaoRelatorio = new SessaoRelatorio(sessao);
		sessaoRelatorioRepository.save(sessaoRelatorio);
	}

}
