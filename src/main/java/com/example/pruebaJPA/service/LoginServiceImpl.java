package com.example.pruebaJPA.service;

import com.example.pruebaJPA.dto.JwtDto;
import com.example.pruebaJPA.dto.LoginDto;
import com.example.pruebaJPA.entity.UserEntity;
import com.example.pruebaJPA.repository.IUserRepository;
import com.example.pruebaJPA.service.securityServices.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements ILoginService {

    AuthenticationManager authenticationManager;

    IUserRepository repository;

    JwtService jwtService;

    public LoginServiceImpl(AuthenticationManager authenticationManager,
                            IUserRepository repository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @Override
    public JwtDto login(LoginDto loginDto) {

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());
        authenticationManager.authenticate(auth);

        UserEntity user = repository.findByUsername(loginDto.getUsername()).get();
        String jwt = jwtService.generate(user, generateExtraClaims(user));
        return new JwtDto(jwt);
    }

    private Map<String, Object> generateExtraClaims(UserEntity user) {
        Map<String, Object> extraClaims = new HashMap<>();

        // Obtengo el nombre real de User
        extraClaims.put("name", user.getName());
        // Obtengo la lista de roles asignados al user
        extraClaims.put("roles", user.getRoles().stream()
                .map(rol -> rol.getName().name()).toList());
        return extraClaims;
    }
}
