package com.alten.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.alten.shop.dao.UserDao;
import com.alten.shop.dao.model.User;
import com.alten.shop.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {
	
	@Autowired
	UserService userService;

	@Autowired
	UserDao userDao;
	
	String adminEmail = "admin@admin.com";
	String adminPassword = "AdminPassword";
	String adminFirstName = "admin";
	String adminUsername = "admin";

	@Override
	public void run(String... args) throws Exception {
		
		// Créer le profil admin s'il n'existe pas
		if(userService.getUserByEmail(adminEmail).isEmpty()) {
			User admin = new User();
			admin.setEmail(adminEmail);
			admin.setFirstName(adminFirstName);
			admin.setUsername(adminUsername);
			
			// Hashage du mot de passe admin
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			admin.setPassword(passwordEncoder.encode(adminPassword));
			
			userDao.save(admin);
		}
		
		
	}
	
	

}
