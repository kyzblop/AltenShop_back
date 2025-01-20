package com.alten.shop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.shop.dao.UserDao;
import com.alten.shop.dao.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public Optional<User> getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
}
