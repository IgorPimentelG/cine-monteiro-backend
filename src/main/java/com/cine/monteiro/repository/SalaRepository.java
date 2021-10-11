package com.cine.monteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.model.cinema.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long>{

}
