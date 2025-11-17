package br.edu.atitus.api_example.services;

import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserServices(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                java.util.Collections.emptyList());
    }

    public UserEntity save(UserEntity user) throws Exception {
        if (user == null)
            throw new Exception("Objeto Nulo");

        if (user.getName() == null || user.getName().isEmpty())
            throw new Exception("Nome inválido");
        user.setName(user.getName().trim());

        // Validação de Email com Regex (Exige @ e ponto)
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().matches(emailRegex))
            throw new Exception("E-mail inválido");
        user.setEmail(user.getEmail().trim());

        // Validação de Senha com Regex (Maiúscula, minúscula e número)
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        if (user.getPassword() == null || user.getPassword().length() < 8 || !user.getPassword().matches(passwordRegex))
            throw new Exception("Senha inválida: Deve conter no mínimo 8 caracteres, uma letra maiúscula, uma minúscula e um número.");

        user.setPassword(encoder.encode(user.getPassword()));

        if (repository.existsByEmail(user.getEmail()))
            throw new Exception("Já existe usuário cadastrado com este e-mail");

        return repository.save(user);
    }
}