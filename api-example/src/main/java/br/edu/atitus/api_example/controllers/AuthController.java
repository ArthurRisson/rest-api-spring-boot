package br.edu.atitus.api_example.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_example.dtos.SigninDTO;
import br.edu.atitus.api_example.dtos.SignupDTO;
import br.edu.atitus.api_example.entities.TypeUser;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.services.UserServices;
import br.edu.atitus.api_example.utils.JwtUtils;

@RestController	
@RequestMapping("/auth")
public class AuthController {
	
	private final UserServices service;
	// Dependências necessárias para o login
	private final AuthenticationManager authManager;
	private final JwtUtils jwtUtils;
	
	public AuthController(UserServices service, AuthenticationManager authManager, JwtUtils jwtUtils) {
		super();
		this.service = service;
		this.authManager = authManager;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserEntity> postSignup(@RequestBody SignupDTO dto) throws Exception{
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(dto, user);
		user.setType(TypeUser.Common);
		
		service.save(user);

		return ResponseEntity.status(201).body(user);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<String> postSignin(@RequestBody SigninDTO signin) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(signin.email(), signin.password()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateTokenFromUsername(authentication.getName());
		
		return ResponseEntity.ok(jwt);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e){
		String message = e.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(message);
	}
}