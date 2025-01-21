package com.alten.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.shop.dao.UserDao;
import com.alten.shop.dao.model.Product;
import com.alten.shop.dao.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	// Trouve un utilisateur avec son email
	public Optional<User> getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	// Affichage du panier de l'utilisateur
	public List<Product> getPanier(Integer idUser) {
		return userDao.findById(idUser).get().getPanierAchat();
	}
	
	// affichage de la liste d'envie de l'utilisateur
	public List<Product> getListeEnvie(Integer idUser) {
		return userDao.findById(idUser).get().getListEnvie();
	}
	
	// Modification des listes de l'utilisateur (panier et liste d'envie) (Cette méthode pourrait être étendue à la modification de tous les champs d'un utilisateur
	public void updateUser(Integer idUser, User user) {
		if(userDao.existsById(idUser)) {
			User userRecup = userDao.findById(idUser).get();
			userRecup.setId(idUser);
			if(!user.getPanierAchat().isEmpty()) {
				userRecup.setPanierAchat(user.getPanierAchat());
			}
			if(!user.getListEnvie().isEmpty()) {
				userRecup.setListEnvie(user.getListEnvie());
			}
			userDao.save(userRecup);
		}
	}
	
}
