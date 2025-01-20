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
		productDao.save(product);
	}
	
	// Modification d'un produit
	public void updateProduct(Product product, Integer idProduct) {
		if(productDao.findById(idProduct) != null) {
			if(product.getCode() != null) {
				productDao.updateCode(idProduct, product.getCode());
			}
			if(product.getName() != null ) {
				productDao.updateName(idProduct, product.getName());
			}
			if(product.getDescription() != null) {
				productDao.updateDescription(idProduct, product.getDescription());
			}
			if(product.getImage() != null) {
				productDao.updateImage(idProduct, product.getImage());
			}
			if(product.getCategory() != null) {
				productDao.updateCategory(idProduct, product.getCategory());
			}
			if(product.getPrice() != null) {
				productDao.updatePrice(idProduct, product.getPrice());
			}
			if(product.getQuantity() != null) {
				productDao.updateQuantity(idProduct, product.getQuantity());
			}
			if(product.getInternalReference() != null) {
				productDao.updateInternalReference(idProduct, product.getInternalReference());
			}
			if(product.getShellId() != null) {
				productDao.updateShellId(idProduct, product.getShellId());
			}
			if(product.getInventoryStatus() != null) {
				productDao.updateInventoryStatus(idProduct, product.getInventoryStatus());
			}
			if(product.getRating() != null) {
				productDao.updateRating(idProduct, product.getRating());
			}
			// La date de mise à jour est quand la méthode est appelée 
			productDao.updateUpdateAt(idProduct, new Date());
		} else {
			System.out.println("Le produit n'existe pas");
		}
	}
	
	// Suppression d'un produit
	public void deleteProduct(Integer idProduct) {
		productDao.deleteById(idProduct);
	}

}
