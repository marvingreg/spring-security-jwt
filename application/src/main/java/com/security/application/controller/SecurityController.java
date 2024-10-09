package com.security.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import jakarta.servlet.http.HttpServletRequest;
 
//just a testing for retrieving CSRF-TOKEN (not implemented but merely for testing)
@Controller
public class SecurityController {
	
	
	@GetMapping("/token")
	public ResponseEntity<Object> getToken(HttpServletRequest request){
		
		Map<String, Object> response = new HashMap<>();		
		response.put("csrfToken", request.getAttribute("_csrf"));
		return new ResponseEntity<Object>(response, HttpStatus.OK);
		
	}

}
