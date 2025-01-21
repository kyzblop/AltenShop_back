package com.alten.shop.config;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.alten.shop.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Autowired
	private UserService userService;

	// Généré par https://jwtsecret.com/generate
	private String jwtSecret = "4e1a2010916bcca000f22d8b5cba0cb845b5967874d0384d6c8692bb8ff2ddee0b01c58d2e2fae04d1fad40f7bdadcc94601253368c5a2d59eea78783363a78a4e051abba98a0ccf2fc849bd1aab0478f2afb87ca47cff5f12f7986e5afbb4a67bcb2e56a3eeb6e4e88a0d7393f38e303a6247a83d9d5e7137c49692be5ffe1a42ad7c70dfa86f68b0c537e008418a02730984426b9c88ce0a03ec8007b77ef59ced4b16844fe97209d31c52d8509ba5279830fce462ff81a00c87b016f2ac0cdf40f922a43404ca539e6f20d339c15a0f004e8ac196c6af194ac3fad124339b93cf487eddfc8f6b79d7ac79baa20d61619718c6c06201ddbd8bd7b4c88ea390";
	private long jwtExpirationDate = 3600000; // Le token sera expiré après une heure
	
	// Méthode de génération du token
	public String generateToken(Authentication authentication) {
		String email = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		Integer idUser = userService.getUserByEmail(email).get().getId();
		
		String token = Jwts.builder()
						.subject(email)
						.issuedAt(expireDate)
						.claim("idUser", idUser)
						.signWith(key(), SignatureAlgorithm.HS256)
						.compact();
		
		return token;
	}
	
	// Méthode pour avoir la clé du token
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	// Méthode pour extraire l'email de l'utilisateur du token
	public String getEmail(String token) {
		return Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	// Méthode pour valider le token
	public boolean validateToken(String token) {
		Jwts.parser()
			.verifyWith((SecretKey) key())
			.build()
			.parse(token);
		
		return true;
	}
	
}
