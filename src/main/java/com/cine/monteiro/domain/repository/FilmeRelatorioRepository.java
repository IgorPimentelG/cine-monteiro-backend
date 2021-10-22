package com.cine.monteiro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.domain.model.relatorio.FilmeRelatorio;

@Repository
public interface FilmeRelatorioRepository extends JpaRepository<FilmeRelatorio, Long> {

}
