package com.alten.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.alten.shop.dao.model.Product;
import com.alten.shop.dao.model.User;
import com.alten.shop.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	// Affichage du panier de l'utilisateur
	@GetMapping("/{idUser}/panier")
	public List<Product> getPanier(@PathVariable Integer idUser) {
		List<Product> panierRecup = userService.getPanier(idUser);
		if(!panierRecup.isEmpty()) {
			return panierRecup;
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Le panier est vide");
		}
	}
	
	// Affichage de la liste d'envie de l'utilisateur
	@GetMapping("/{idUser}/listeEnvie")
	public List<Product> getListeEnvie(@PathVariable Integer idUser) {
		List<Product> listeEnvie = userService.getListeEnvie(idUser);
		if(!listeEnvie.isEmpty()) {
			return listeEnvie;
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "La liste d'envie est vide");
		}
	}
	
	// Modification des deux listes de l'utilisateur
	@PatchMapping("/{idUser}/modification")
	public ResponseEntity<String> updatePanier(@PathVariable Integer idUser, @RequestBody User user){
		try {
			userService.updateUser(idUser, user);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("L'utilisateur a bien été modifié");
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}

}
