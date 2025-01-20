package com.alten.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDao.findByEmail(username).orElseThrow( () -> 
			new UsernameNotFoundException("Il n'y a pas d'utilisateur lié à cet email"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), null);
	}

}