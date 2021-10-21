package com.cine.monteiro.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cine.monteiro.model.users.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	public Cliente findByEmail(String email);

}
