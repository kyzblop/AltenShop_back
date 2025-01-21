package com.alten.shop.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.alten.shop.dao.model.User;



public interface UserDao extends CrudRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	
}
