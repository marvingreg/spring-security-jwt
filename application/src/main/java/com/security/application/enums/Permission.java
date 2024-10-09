package com.security.application.enums;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

	ADMIN_READ("admin:read"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_CREATE("admin:create"),
	ADMIN_DELETE("admin:delete"),
	
	USER_READ("user:read"),
	USER_UPDATE("user:update");
	
	
	@Getter
	private final String permName;
	
	Permission(String permName) {
		this.permName = permName;
	}
	
	


}
