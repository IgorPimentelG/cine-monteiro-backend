package com.cine.monteiro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.domain.model.user.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
	
	public Administrador findByEmail(String email);

}
