package com.cine.monteiro.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.cine.monteiro.domain.model.user.User;
import com.cine.monteiro.domain.repository.UserRepository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO!");
		}

		return user;
	}

}
