package com.security.application.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.security.application.enums.Role;
import com.security.application.model.Users;

@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepo;
	
	@AfterEach
	void reset() {
		userRepo.deleteAll();
	}
	
	@Test
	void findByUserNameTest() {
		//given
		Users user = new Users("1",
							  "Greg",
							  "Adversario",
							  "marvingreg22@gmail.com",
							  "password_1",
							  "marvin.greg",
							   Role.ADMIN);
		userRepo.save(user);
		
		//when
		assertNotNull(userRepo.findByUsername("marvin.greg"));
	}
	
	@Test
	void findByUserIdTest() {
		
		Users user = new Users("1",
							  "Greg",
							  "Adversario",
							  "marvingreg22@gmail.com",
							  "password_1",
							  "marvin.greg",
							   Role.ADMIN);
		userRepo.save(user);
		assertNotNull(userRepo.findById("1"));
	}

}
