package com.cine.monteiro.domain.model.user;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Table(name = "TB_PROFILE")
@Data
public class Profile implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String profile;
	
	public Profile() {}

	@Override
	public String getAuthority() {
		return profile;
	}

}
