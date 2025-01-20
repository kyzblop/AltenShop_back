package com.alten.shop.config;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.alten.shop.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Autowired
	private UserService userService;

	// Généré par https://jwtsecret.com/generate
	private String jwtSecret = "1754075ff91dd43a501c62a1a42112fc96de8922e209370af120c3a3d6bfbb2e5f6ac944a311e4e70f587f17669f72d23e235f9f1291301e252f685f2bf8f459";
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
