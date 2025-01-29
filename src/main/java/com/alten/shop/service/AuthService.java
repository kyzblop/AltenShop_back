package com.alten.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alten.shop.config.JwtTokenProvider;
import com.alten.shop.dao.UserDao;
import com.alten.shop.dao.model.User;
import com.alten.shop.dto.LoginDto;
import com.alten.shop.exception.EmailAlreadyUsedException;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDao userDao;
	
	// Méthode de connexion
	public String login(LoginDto loginDto) {
		
		// Authentification du couple email/password
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getEmail(), 
				loginDto.getPassword()));
		
		// Propagation de l'information d'authentification au reste de l'application
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Generation du token avec l'information de l'authentification
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;
		
	}
	
	// Méthode d'inscription d'utilisateur
	public User register(User user) throws EmailAlreadyUsedException {
		
		// Verification si l'email n'est pas déjà utilisé
		String email = user.getEmail();
		if(userDao.findByEmail(email).isPresent()) {
			throw new EmailAlreadyUsedException("Cet email est déjà utilisé");
		}
		
		// Supprimer l'id pour sauvegarder une nouvelle entrée
		user.setId(null);
		
		// Encoder le mot de passe
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedpassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedpassword);
		
		// Sauvegarde de l'utilisateur dans la BDD
		userDao.save(user);
		
		return user;
	}
}
