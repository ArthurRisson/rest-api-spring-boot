package br.edu.atitus.api_example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ConfigSecurity {
	
	private final AuthTokenFilter authTokenFilter;
	
	public ConfigSecurity(AuthTokenFilter authTokenFilter) {
		this.authTokenFilter = authTokenFilter;
	}

	@Bean
	SecurityFilterChain getSecurityFilter(HttpSecurity http) throws Exception{
		http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Desabilita sessões (stateless)
			.csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/**").permitAll() // Libera endpoints de autenticação (signup/signin)
				.requestMatchers("/ws**", "/ws/**").authenticated() // Protege rotas /ws
				.anyRequest().permitAll())
			// CORREÇÃO: Adiciona o filtro JWT antes do filtro de usuário/senha padrão
			.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}