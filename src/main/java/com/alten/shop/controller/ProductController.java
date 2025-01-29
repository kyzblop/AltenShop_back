package com.alten.shop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.alten.shop.dao.model.Product;
import com.alten.shop.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	// Récupération de la liste des produits
	@GetMapping("")
	public List<Product> getAllProduct() {
		List<Product> listProductRecup = productService.getAllProduct();
		if(!listProductRecup.isEmpty()) {
			return listProductRecup;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Liste de produit vide");
		}
	}
	
	// Récupération d'un produit avec son id
	@GetMapping("/{idProduct}")
	public Optional<Product> getProductById(@PathVariable Integer idProduct) {
		Optional<Product> productRecup = productService.getProductById(idProduct);
		if(!productRecup.isEmpty()) {
			return productRecup;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "produit non trouvé");
		}
	}
	
	// Création d'un produit
	@PostMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> insertProduct(@RequestBody Product product) {
		try {
			productService.insertProduct(product);
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.build();
					
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
	
	// Modification d'un produit
	@PatchMapping("/{idProduct}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> updateProduct(@PathVariable Integer idProduct, @RequestBody Product product) {
		try {
			productService.updateProduct(idProduct, product);
			return ResponseEntity
					.status(HttpStatus.OK)
					.build();
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
	
	// Suppression d'un produit
	@DeleteMapping("/{idProduct}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer idProduct) {
		try {
			productService.deleteProduct(idProduct);
			return ResponseEntity
					.status(HttpStatus.OK)
					.build();
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
	
}
