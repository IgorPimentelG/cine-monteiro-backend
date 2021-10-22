package com.cine.monteiro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.domain.model.cinema.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long>{

}
