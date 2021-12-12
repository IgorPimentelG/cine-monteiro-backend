package com.cine.monteiro.domain.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cine.monteiro.domain.model.cinema.Sessao;

@Transactional
@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

	
	@Query("SELECT s FROM Sessao s WHERE s.isAtiva = true AND s.horaDeInicioExibicao > ?1")
	List<Sessao> buscarSessoesDoDia(LocalTime horaConsulta);
	
}
