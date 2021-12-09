package com.cine.monteiro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.domain.model.user.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{
	
	Profile findByProfile(String profile);

}
