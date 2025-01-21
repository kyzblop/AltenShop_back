package com.alten.shop.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.alten.shop.dao.UserDao;
import com.alten.shop.dao.model.User;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	
	private String adminEmail = "admin@admin.com";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDao.findByEmail(username).orElseThrow( () -> 
			new UsernameNotFoundException("Il n'y a pas d'utilisateur lié à cet email"));
		
		// Autorité
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		if(user.getEmail().equals(adminEmail)) {
			// Seul l'utilisateur avec l'email admin a une autorité ADMIN
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
		} else {
			authorities.add(new SimpleGrantedAuthority("USER"));
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}