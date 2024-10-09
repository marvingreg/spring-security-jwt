package com.security.application.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.application.model.Users;
import com.security.application.properties.EmailProperties;
import com.security.application.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	EmailProperties emailProperties;
	
	@Autowired	
	UserRepository userRepository;
	
	@Autowired
	CustomerUserDetailsService userDetails;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	EmailService emailService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public UserService(UserRepository userRepo) {
		this.userRepository = userRepo;
	}
	
	
	public ResponseEntity<Object> getUser(String globalId) throws Exception{
		
		Users user = userRepository.findById(globalId)
				.orElseThrow(()-> new Exception("User not found"));
		
		return new ResponseEntity<Object>(user,HttpStatus.OK);
	}
	
	public ResponseEntity<Object> postUser(Users user){
		
		user.setId(generateId());
		user.setPassword(encoder.encode(user.getPassword()));
		
		userRepository.save(user);
		user.setPassword(null);
		
		try {
			emailService.sendEmail(emailProperties.getFrom(), user.getEmail(), "User Sign Up");
		} catch (Exception e) {
			System.out.println("Sending of email failed");
		}
		
		return new ResponseEntity<Object>(user,HttpStatus.OK);
	}

	public ResponseEntity<Object> putUser(Users user, String globalId){
		
		Users userToUpdate = userRepository.findById(globalId).orElseThrow(
				() -> new NullPointerException("User cannot be found"));

		userToUpdate.setPassword((user.getPassword() != null ) ? encoder.encode(user.getPassword()) : userToUpdate.getPassword());
		userToUpdate.setUsername((user.getUsername() != null ) ? user.getUsername() : userToUpdate.getUsername());
		userToUpdate.setEmail((user.getEmail() != null) ? user.getEmail() : userToUpdate.getEmail());
		userToUpdate.setRole((user.getRole() != null) ? user.getRole() : userToUpdate.getRole());
		
		userRepository.save(userToUpdate);
		
		return new ResponseEntity<Object>(userToUpdate, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> postLogin(Users user) throws Exception{
		
		Authentication auth = authManager
				.authenticate
				(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		Users fetchUser = userRepository.findByUsername(user.getUsername());
		
		String token = "";
		
		if(auth.isAuthenticated()) {
			token = jwtService.generateToken(fetchUser);
			emailService.sendEmail(emailProperties.getFrom(), fetchUser.getEmail(), "Log in Verification");
			
		}else{
			return new ResponseEntity<Object>("Log in Failed",HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<Object>(token,HttpStatus.OK);
	}
	
	public String generateId() {
		
		LocalDateTime ldt = LocalDateTime.now();
		return ldt.getYear()+"."+ldt.getNano()+"."+ldt.getDayOfMonth()+ldt.getMonthValue()+ldt.getMinute();
	}
}
