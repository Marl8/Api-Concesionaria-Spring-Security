package com.example.pruebaJPA.service;

import com.example.pruebaJPA.dto.UserDto;
import com.example.pruebaJPA.dto.UserResponseDto;
import com.example.pruebaJPA.dto.UserSaveDto;
import com.example.pruebaJPA.dto.UserUpdateDto;
import com.example.pruebaJPA.entity.UserEntity;
import com.example.pruebaJPA.exception.UserGenericException;
import com.example.pruebaJPA.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements IServiceUser {

    IUserRepository repository;

    PasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET => usando Paginación
    @Override
    public Page<UserDto> getUsers(Pageable pagination) {

        // Hay un findAll() que acepta como parámetro una paginación
        Page<UserEntity> users = repository.findAll(pagination);

        if(users == null) {
            throw new UserGenericException("No users found");
        }
        Page<UserDto> usersDto = users.map(u ->{
              return new UserDto(u.getId(), u.getUsername(), u.getName(), u.getRoles());
        });
        return usersDto;
    }

    @Override
    public UserResponseDto save(UserSaveDto userDto) {
        ObjectMapper mapper = new ObjectMapper();
        UserEntity user = mapper.convertValue(userDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(verificarSiExiste(user.getUsername())){
            throw new UserGenericException("El user ya existe");
        }
        UserEntity guardado = repository.save(user);
        if(guardado == null) {
            throw new UserGenericException("Error en el guardado");
        }
        return new UserResponseDto("Usuario guardado con éxito");
    }

    @Transactional
    @Override
    public UserResponseDto update(UserUpdateDto userDto) {
        ObjectMapper mapper = new ObjectMapper();
        UserEntity user = mapper.convertValue(userDto, UserEntity.class);
        Optional<UserEntity> encontrado = repository.findById(user.getId());

        if(encontrado.isPresent()){
            UserEntity modificado = encontrado.get();
            modificado.setName(user.getName());
            modificado.setUsername(user.getUsername());
            modificado.setPassword(passwordEncoder.encode(user.getPassword()));
            modificado.setRoles(user.getRoles());
        }else {
            throw new UserGenericException("Usuario inexistente");
        }
        return new UserResponseDto("El usuario fue modificado con éxito");
    }

    @Override
    public UserResponseDto delete(Long id) {
        Optional<UserEntity> encontrado = repository.findById(id);

        //encontrado.ifPresent(userEntity -> repository.delete(userEntity));
        if (encontrado.isPresent()) {
            repository.delete(encontrado.get());
        }else{
            throw new UserGenericException("Usuario inexistente");
        }
        return new UserResponseDto("El usuario fue eliminado con éxito");
    }

    private boolean verificarSiExiste(String username){

        Optional<UserEntity> user = repository.findByUsername(username);
        if(user.isPresent()){
            return true; // si está presente es que no se eliminó
        }
        return false;
    }
}
