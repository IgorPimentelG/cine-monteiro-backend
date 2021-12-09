package com.cine.monteiro.domain.DTO;

import lombok.Data;

@Data
public class DTOUserAuth {
	
	private String token;
	private String profile;
	
	public DTOUserAuth(String token, String profile) {
		this.token = token;
		this.profile = profile;
	}
	
}
