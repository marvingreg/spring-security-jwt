package com.security.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.security.application.model.Users;
import com.security.application.service.UserService;

import jakarta.websocket.server.PathParam;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/{globalId}/users")
	public ResponseEntity<Object> getUser(@PathVariable String globalId) throws Exception
	{
		return userService.getUser(globalId);
	}
	
	@PutMapping("/{globalId}/users")
	public ResponseEntity<Object> putUser(@PathVariable String globalId, @RequestBody Users user){
		return userService.putUser(user, globalId);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Object> postUser(@RequestBody Users user){
		return userService.postUser(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> postLogin(@RequestBody Users user) throws Exception{
		
		return userService.postLogin(user);
	}

}
