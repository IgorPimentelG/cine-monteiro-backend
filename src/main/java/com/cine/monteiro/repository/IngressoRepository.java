package com.cine.monteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.model.cinema.Ingresso;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

}
