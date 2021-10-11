package com.cine.monteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRelatorioRepository extends JpaRepository<SessaoRelatorioRepository, Long> {

}
