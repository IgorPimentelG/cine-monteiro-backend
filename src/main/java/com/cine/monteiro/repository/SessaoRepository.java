package com.cine.monteiro.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.model.cinema.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

	
	@Query("SELECT s FROM Sessao s WHERE s.isAtiva = true AND s.horaDeInicioExibicao > ?1")
	List<Sessao> buscarSessoesDoDia(LocalTime horaConsulta);
	
}
