package com.cine.monteiro.api.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cine.monteiro.api.filter.JwtFilter;
import com.cine.monteiro.domain.services.ImplementsUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private ImplementsUserDetailsService userDatDetailsService;
	@Autowired private JwtFilter jwtFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
		.and().csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, "/user/**").permitAll()
		.antMatchers(HttpMethod.PUT, "/user/recuperar").permitAll()
		.antMatchers(HttpMethod.PUT, "/user/atuailizar").hasAuthority("CLIENT")
		.antMatchers(HttpMethod.DELETE, "/user/deletar").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/user/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/sala/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/sala/deletar/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/sala/**").hasAnyAuthority("ADMIN", "CLIENT")
		.antMatchers(HttpMethod.POST, "/filme/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.PUT, "/filme/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/filme/**").hasAnyAuthority("ADMIN", "CLIENT")
		.antMatchers(HttpMethod.POST, "/ingresso/**").hasAuthority("CLIENT")
		.antMatchers(HttpMethod.DELETE, "/ingresso/cancelar/**").hasAuthority("CLIENT")
		.antMatchers(HttpMethod.GET, "/ingresso/**").hasAnyAuthority("ADMIN", "CLIENT")
		.antMatchers(HttpMethod.POST, "/sessao/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.PUT, "/sessao/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/sessao/**").hasAnyAuthority("ADMIN", "CLIENT")
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDatDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
