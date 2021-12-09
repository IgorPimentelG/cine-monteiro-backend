package com.cine.monteiro.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil  {
	
	private String secret = "cine-monteiro";
	
	public String gerarToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return criarToken(claims, username);
	}
	
	public Boolean validarToken(String token, UserDetails userDetails) {
		String username = extrairUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public String extrairUsername(String token) {
		return extrairClaim(token, Claims::getSubject);
	}
	
	public Date extrairTempoValidade(String token) {
		return extrairClaim(token, Claims::getExpiration);
	}
	
	public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extrairAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extrairAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extrairTempoValidade(token).before(new Date());
	}

	private String criarToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
	}

}
