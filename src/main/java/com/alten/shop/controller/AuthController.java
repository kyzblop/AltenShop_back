package com.alten.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.shop.dao.model.User;
import com.alten.shop.dto.LoginDto;
import com.alten.shop.dto.ResponseLoginDto;
import com.alten.shop.exception.EmailAlreadyUsedException;
import com.alten.shop.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	// Inscription d'un utilisateur
	@PostMapping("/account")
	public ResponseEntity<User> insertUser(@RequestBody User user) throws EmailAlreadyUsedException {
		User userRegister = authService.register(user);
		return new ResponseEntity<User>(userRegister, HttpStatus.CREATED);
	}
	
	// Méthode pour gérer l'exception
	@ExceptionHandler(EmailAlreadyUsedException.class)
	public ResponseEntity<String> handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(e.getMessage());
	}
	
	// Login d'un utilisateur
	@PostMapping("/token")
	public ResponseEntity<ResponseLoginDto> loginUser(@RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);
		ResponseLoginDto response = new ResponseLoginDto();
		response.setToken(token);
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(response);
		
	}

}
