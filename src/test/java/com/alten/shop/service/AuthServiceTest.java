package com.alten.shop.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alten.shop.config.JwtTokenProvider;
import com.alten.shop.dao.UserDao;
import com.alten.shop.dao.model.User;
import com.alten.shop.dto.LoginDto;
import com.alten.shop.exception.EmailAlreadyUsedException;

@SpringBootTest
public class AuthServiceTest {
	
	@InjectMocks
	private AuthService authService;
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	private Authentication authentication;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private JwtTokenProvider jwtTokenProvider;
	
	
	private User user;
	private LoginDto loginDto;
	
	@BeforeEach
	public void setUp() {
		
		MockitoAnnotations.openMocks(this);
		
		passwordEncoder = new BCryptPasswordEncoder();
		
		// Initilisation d'un user 
		user = new User();
		user.setEmail("mail@mail.com");
		user.setPassword("password");
		
		loginDto = new LoginDto();
		loginDto.setEmail("mail@mail.com");
		loginDto.setPassword("password");
	
	}
	
	// Test de login réussi
	@Test
	public void testLogin() {
		// Quand la méthode authentifie l'utilisateur, retourne l'authentification
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
			.thenReturn(authentication);
		when(jwtTokenProvider.generateToken(authentication))
			.thenReturn("token");
		
		// Appel de la fonction testée
		String token = authService.login(loginDto);
		
		// Véréfication du resultat : Que le token est bien généré et que les fonction mockés sont appelées une seule fois
		assertEquals("token", token);
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(jwtTokenProvider).generateToken(authentication);

	}
	
	// Test de login échoué
	@Test
	public void testLogin_withWrongLoginDto() throws AuthenticationException {
		// Quand la méthode authentifie l'utilisateur, retourne l'authentification
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
			.thenThrow(BadCredentialsException.class);
		when(jwtTokenProvider.generateToken(authentication))
			.thenReturn("token");
		
		
		// Vérification du resultat : Que la fonction soulève bien une exception, que la fonction d'authntification est appelée une fois et que le token n'est pas généré
		assertThrows(BadCredentialsException.class, () -> {
			authService.login(loginDto);
		});
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(jwtTokenProvider, times(0)).generateToken(authentication);
		

	}
	
	// Test d'un enregistrement d'utilisateur réussi 
	@Test
	public void testRegister() throws EmailAlreadyUsedException {
		
		// Quand la méthode controle si l'email utilisé existe déjà, retourner un objet vide
		when(userDao.findByEmail(any(String.class)))
			.thenReturn(Optional.empty());
		
		// Appelle de la fonction testée
		User userSaved = authService.register(user);
		
		// Controle du resultat
		assertNotNull(userSaved);
		assertEquals("mail@mail.com", userSaved.getEmail());
		assertTrue(passwordEncoder.matches("password", userSaved.getPassword()));
		
	}
	
	// Test d'un enregistrement d'utilisateur échoué car l'email existe déjà 
	@Test
	public void testRegister_withEmailAlreadyExist() throws EmailAlreadyUsedException {
		
		// Quand la méthode controle si l'email utilisé existe déjà, retourner un objet user
		when(userDao.findByEmail(any(String.class)))
			.thenReturn(Optional.of(user));
		
		// Controle que l'exception est bien générée lors de l'appel de la fonction testée et que la fonction save n'est pas appelée
		assertThrows(EmailAlreadyUsedException.class, () -> {
			authService.register(user);
		});
		verify(userDao, times(0)).save(any(User.class));
		
	}

}
