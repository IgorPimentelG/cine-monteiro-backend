package com.cine.monteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.model.users.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, String> {

}
