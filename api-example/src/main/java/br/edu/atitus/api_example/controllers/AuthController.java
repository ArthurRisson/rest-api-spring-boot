package br.edu.atitus.api_example.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_example.dtos.SignupDTO;
import br.edu.atitus.api_example.entities.TypeUser;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.services.UserServices;

@RestController	
@RequestMapping("/auth")
public class AuthController {
	
	//AuthController DEPENDE de um objeto UserService
	private final UserServices service;
	
	public AuthController(UserServices service) {
		super();
		this.service = service;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserEntity> postSignup(
			@RequestBody SignupDTO dto) throws Exception{
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(dto, user);
		user.setType(TypeUser.Common);
		
		service.save(user);

		return ResponseEntity.status(201).body(user);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e){
		String message = e.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(message);
	}

}



