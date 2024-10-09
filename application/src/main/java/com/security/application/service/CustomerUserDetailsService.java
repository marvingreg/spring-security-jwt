package com.security.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.application.model.UserPrincipal;
import com.security.application.model.Users;
import com.security.application.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	
	public CustomerUserDetailsService(UserRepository userRepo) {
		this.userRepository = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		
		Users user = Optional.of(userRepository.findByUsername(username.toLowerCase()))
				.orElseThrow(()-> new UsernameNotFoundException("Username does not exist"));
	
		return new UserPrincipal(user);
	}

}
