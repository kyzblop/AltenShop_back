package com.alten.shop.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.shop.dao.ProductDao;
import com.alten.shop.dao.model.Product;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	// Récupération de la liste des produits
	public List<Product> getAllProduct() {
		return (List<Product>) productDao.findAll();
	}
	
	// Récupération d'un produit avec son id
	public Optional<Product> getProductById(Integer idProduct) {
		return productDao.findById(idProduct);
	}
	
	// Création d'un produit
	public void insertProduct(Product product) {
		product.setId(null);
		product.setCreatedAt(new Date());
		productDao.save(product);
	}
	
	// Modification d'un produit
	public void updateProduct(Integer idProduct, Product product) {
		if(productDao.existsById(idProduct)) {
			Product productRecup = productDao.findById(idProduct).get();
			productRecup.setId(idProduct);
			
			if(product.getCode() != null) {
				productRecup.setCode(product.getCode());
			}
			if(product.getName() != null ) {
				productRecup.setName(product.getName());
			}
			if(product.getDescription() != null) {
				productRecup.setDescription(product.getDescription());
			}
			if(product.getImage() != null) {
				productRecup.setImage(product.getImage());
			}
			if(product.getCategory() != null) {
				productRecup.setCategory(product.getCategory());
			}
			if(product.getPrice() != null) {
				productRecup.setPrice(product.getPrice());
			}
			if(product.getQuantity() != null) {
				productRecup.setQuantity(product.getQuantity());
			}
			if(product.getInternalReference() != null) {
				productRecup.setInternalReference(product.getInternalReference());
			}
			if(product.getShellId() != null) {
				productRecup.setShellId(product.getShellId());
			}
			if(product.getInventoryStatus() != null) {
				productRecup.setInventoryStatus(product.getInventoryStatus());
			}
			if(product.getRating() != null) {
				productRecup.setRating(product.getRating());
			}
			// La date de mise à jour est quand la méthode est appelée 
			productRecup.setUpdateAt(new Date());
			productDao.save(productRecup);
		} else {
			System.out.println("Le produit n'existe pas");
		}
	}
	
	// Suppression d'un produit
	public void deleteProduct(Integer idProduct) {
		productDao.deleteById(idProduct);
	}

}
