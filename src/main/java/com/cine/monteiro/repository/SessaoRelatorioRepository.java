package com.cine.monteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.model.relatorio.SessaoRelatorio;

@Repository
public interface SessaoRelatorioRepository extends JpaRepository<SessaoRelatorio, Long> {

}
