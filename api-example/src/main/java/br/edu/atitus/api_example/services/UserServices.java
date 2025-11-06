package br.edu.atitus.api_example.services;

import br.edu.atitus.api_example.entities.UserEntity;

public class UserServices {

	public UserEntity save(UserEntity user) throws Exception {
		if (user == null)
			throw new Exception("Objeto nulo"); 
		if (user.getName()== null || user.getName().isEmpty())
			throw new Exception("Nome inválido");
		user.setName(user.getName().trim());

		if (user.getEmail()== null || user.getEmail().isEmpty())
			throw new Exception("Nome inválido");
		user.setEmail(user.getEmail().trim());

		if (user.getPassword() == null
				|| user.getPassword().isEmpty()
				|| user.getPassword().length() < 8)
			throw new Exception("Password negado");
		
		
		
		//TODO enviar para a camada repository
		return user;
	}
}
