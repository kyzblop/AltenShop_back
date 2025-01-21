package com.alten.shop.dao.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String username;
	
	@Column
	private String firstName;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToAny
	@JoinTable(name="user_panier",
					joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
					inverseJoinColumns = @JoinColumn(name="product_id", referencedColumnName = "id"))
	private List<Product> panierAchat;
	
	@ManyToAny
	@JoinTable(name="user_envie",
					joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
					inverseJoinColumns = @JoinColumn(name="product_id", referencedColumnName = "id"))
	private List<Product> listEnvie;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Product> getPanierAchat() {
		return panierAchat;
	}

	public void setPanierAchat(List<Product> panierAchat) {
		this.panierAchat = panierAchat;
	}

	public List<Product> getListEnvie() {
		return listEnvie;
	}

	public void setListEnvie(List<Product> listEnvie) {
		this.listEnvie = listEnvie;
	}
	
	
	
	
	
}
