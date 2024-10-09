package com.security.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.security.application.enums.Role;
import com.security.application.model.Users;
import com.security.application.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepo;
	
	@Mock
	private UserService userService;
	
	@Mock
	private CustomerUserDetailsService userDetails;
	
	
	@BeforeEach
	void initialize() {
		userService = new UserService(userRepo);
		userDetails = new CustomerUserDetailsService(userRepo);
	}
	
	@Test
	void canAddUser() throws Exception {
		
		Users user = new Users("1",
				  "Greg",
				  "Adversario",
				  "marvingreg22@gmail.com",
				  "password_1",
				  "marvin.greg",
				   Role.ADMIN);
		
		//when
		userService.postUser(user);
		
		//then
		ArgumentCaptor<Users> userArgCaptor = 
				ArgumentCaptor.forClass(Users.class);
		
		verify(userRepo).save(userArgCaptor.capture());
		
		Users captUser = userArgCaptor.getValue();
		
		assertThat(captUser).isEqualTo(user);
	}
	
	@Test
	void canFindByUsername() throws Exception {
		
		Users user = new Users("1",
				  "Greg",
				  "Adversario",
				  "marvingreg22@gmail.com",
				  "password_1",
				  "marvin.greg",
				   Role.ADMIN);
		
		//when
		userService.postUser(user);
		
		userDetails.loadUserByUsername("marvin.greg");
		
		verify(userRepo).findByUsername("marvin.greg");
		
		
	}
	

}
