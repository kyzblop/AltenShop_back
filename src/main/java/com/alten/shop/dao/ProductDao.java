package com.alten.shop.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alten.shop.dao.model.InventoryStatus;
import com.alten.shop.dao.model.Product;

import jakarta.transaction.Transactional;

public interface ProductDao extends CrudRepository<Product, Integer>{
	
	// Méthode pour mettre à jour le code du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.code = :code WHERE p.id = :idProduct")
	public void updateCode(Integer idProduct, String code);
	
	// Méthode pour mettre à jour le nom du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.name = :name WHERE p.id = :idProduct")
	public void updateName(Integer idProduct, String name);
	
	// Méthode pour mettre à jour la description du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.description = :description WHERE p.id = :idProduct")
	public void updateDescription(Integer idProduct, String description);
	
	// Méthode pour mettre à jour l'url de l'image du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.image = :image WHERE p.id = :idProduct")
	public void updateImage(Integer idProduct, String image);
	
	// Méthode pour mettre à jour la categorie du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.category = :category WHERE p.id = :idProduct")
	public void updateCategory(Integer idProduct, String category);
	
	// Méthode pour mettre à jour le prix du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.price = :price WHERE p.id = :idProduct")
	public void updatePrice(Integer idProduct, Double price);
	
	// Méthode pour mettre à jour la quantité du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.quantity = :quantity WHERE p.id = :idProduct")
	public void updateQuantity(Integer idProduct, Integer quantity);
	
	// Méthode pour mettre à jour la référence du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.internalReference = :internalReference WHERE p.id = :idProduct")
	public void updateInternalReference(Integer idProduct, String internalReference);
	
	// Méthode pour mettre à jour le shell id du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.shellId = :shellId WHERE p.id = :idProduct")
	public void updateShellId(Integer idProduct, Integer shellId);
	
	// Méthode pour mettre à jour le status d'inventaire du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.inventoryStatus = :inventoryStatus WHERE p.id = :idProduct")
	public void updateInventoryStatus(Integer idProduct, InventoryStatus inventoryStatus);
	
	// Méthode pour mettre à jour la note du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.rating = :rating WHERE p.id = :idProduct")
	public void updateRating(Integer idProduct, Double rating);
	
	// Méthode pour mettre à jour la date de mise à jour du produit
	@Modifying
	@Transactional
	@Query("UPDATE Product p set p.updateAt = :updateAt WHERE p.id = :idProduct")
	public void updateUpdateAt(Integer idProduct, Date updateAt);

}
