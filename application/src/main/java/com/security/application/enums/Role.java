package com.security.application.enums;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;

public enum Role {
	
	ADMIN(Set.of(Permission.ADMIN_READ,
			Permission.ADMIN_CREATE, 
			Permission.ADMIN_DELETE, 
			Permission.ADMIN_UPDATE)),
	USER(Set.of(Permission.USER_READ,
			Permission.USER_UPDATE));
	
	
	Role(Set<Permission> permissions){
		this.permissions = permissions;
	}
	
	private final Set<Permission> permissions;
	
	public Set<Permission> getPermissions(){
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getAuthorities(){
		
		var authorities = Collections.asSet(permissions
				.stream()
				.map(auth -> new SimpleGrantedAuthority(auth.name()))
				.collect(Collectors.toSet()));
		
		authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		
		return authorities;
	}
}
