package com.cine.monteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.model.relatorio.SalaRelatorio;

@Repository
public interface SalaRelatorioRepository extends JpaRepository<SalaRelatorio, Long> {

}
