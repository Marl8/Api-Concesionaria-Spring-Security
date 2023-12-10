package com.example.pruebaJPA;

import com.example.pruebaJPA.entity.ERol;
import com.example.pruebaJPA.entity.Rol;
import com.example.pruebaJPA.entity.UserEntity;
import com.example.pruebaJPA.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

@SpringBootApplication
public class PruebaJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaJpaApplication.class, args);
	}

	/*
	// Generar un usuario inicial para poder hacer login
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	IUserRepository  repository;

	@Bean
	CommandLineRunner init(){ // Comando que se ejecuta al iniciar el programa
		return args -> {
			UserEntity user = UserEntity.builder()
					.name("Victor Sosa")
					.username("userPrueba")
					.password(passwordEncoder.encode("12345"))
					.roles(Set.of(Rol.builder()
							.name(ERol.valueOf(ERol.ADMIN.name()))
							.build()))
					.build();
			repository.save(user);
		};
	}**/
}
