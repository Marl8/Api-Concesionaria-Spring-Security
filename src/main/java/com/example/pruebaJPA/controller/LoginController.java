package com.example.pruebaJPA.controller;

import com.example.pruebaJPA.dto.LoginDto;
import com.example.pruebaJPA.service.ILoginService;
import com.example.pruebaJPA.service.LoginServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    ILoginService service;

    public LoginController(LoginServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
    //return new ResponseEntity<>(service.login(loginDto), HttpStatus.OK);
    return ResponseEntity.status(HttpStatus.OK).body(service.login(loginDto));
    }
}
