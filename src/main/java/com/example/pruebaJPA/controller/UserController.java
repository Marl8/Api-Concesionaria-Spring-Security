package com.example.pruebaJPA.controller;

import com.example.pruebaJPA.dto.UserSaveDto;
import com.example.pruebaJPA.dto.UserUpdateDto;
import com.example.pruebaJPA.service.IServiceUser;
import com.example.pruebaJPA.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    IServiceUser service;

    public UserController(UserServiceImpl serviceUser) {
        this.service = serviceUser;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@PageableDefault (size = 2, sort = {"username"}) Pageable pageable){
        return new ResponseEntity<>(service.getUsers(pageable), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody UserSaveDto userDto){
    return new ResponseEntity<>(service.save(userDto), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDto userDto){
        return new ResponseEntity<>(service.update(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@Valid @RequestParam Long id){
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
