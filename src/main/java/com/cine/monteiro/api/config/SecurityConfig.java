package com.cine.monteiro.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cine.monteiro.domain.services.ImplementsUserDetailsService;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementsUserDetailsService userDatDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/user/cadastrar").permitAll()
		.anyRequest()
		.authenticated().and()
		.httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {		
		auth.inMemoryAuthentication()
		.withUser("user_admin")
		.password(new BCryptPasswordEncoder().encode("1234"))
		.roles("ADMIN")
		.and()
		.withUser("user_client")
		.password(new BCryptPasswordEncoder().encode("1234"))
		.roles("CLIENT");
		//auth.userDetailsService(userDatDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
