package com.alten.shop.dao;

import org.springframework.data.repository.CrudRepository;

import com.alten.shop.dao.model.Product;


public interface ProductDao extends CrudRepository<Product, Integer>{
	
}
