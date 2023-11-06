package com.example.pruebaJPA.utils;

import com.example.pruebaJPA.dto.UserDto;
import com.example.pruebaJPA.dto.UserSaveDto;
import com.example.pruebaJPA.dto.UserUpdateDto;
import com.example.pruebaJPA.entity.ERol;
import com.example.pruebaJPA.entity.Rol;
import com.example.pruebaJPA.entity.UserEntity;
import java.util.*;

public class ObjectUtils {

    public static UserDto userDto(){
        Rol rol = new Rol(1L, ERol.ADMIN);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        return new UserDto(1L, "Juan Perez", "prueba", roles);
    }

    public static UserEntity user(){
        Rol rol = new Rol(1L, ERol.ADMIN);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        UserEntity user = new UserEntity();
        user.setName("Juan Perez");
        user.setUsername("prueba");
        user.setPassword("123");
        user.setRoles(roles);
        return user;
    }

    public static UserSaveDto userSaveDto(){
        Rol rol = new Rol(1L, ERol.ADMIN);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        return new UserSaveDto( "Juan Perez", "prueba", "123", roles);
    }

    public static UserUpdateDto userUpdateDto(){
        Rol rol = new Rol(1L, ERol.ADMIN);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        return new UserUpdateDto( 1L, "Juan Perez", "prueba", "123", roles);
    }

    public static Optional<UserEntity> userOptional(){
        Rol rol = new Rol(1L, ERol.ADMIN);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        UserEntity user = new UserEntity(1L, "Juan Perez", "prueba", "123", roles);

        /*
        * Devuelve el Objeto di no esta devuelve un NullPointerException
        *
        * https://gustavopeiretti.com/java-8-optional/
        * */
        Optional<UserEntity> op = Optional.of(user);

        // Si no esta presente retorna un NULL
        Optional<UserEntity> optional = Optional.ofNullable(user);
        return op;
    }

    public static List<UserDto> listaUsersDto(){

        List<UserDto> users = new ArrayList<>();
        Rol rol = new Rol(1L, ERol.ADMIN);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        UserDto userDto1 = new UserDto(1L, "Juan Perez", "prueba", roles);

        Rol rol1 = new Rol(2L, ERol.USER);
        Set<Rol> roles1 = new HashSet<>();
        roles.add(rol1);
        UserDto userDto2 = new UserDto(2L, "Carlos Garcia", "garcia", roles1);

        users.add(userDto1);
        users.add(userDto2);
        return users;
    }

    public static List<UserEntity> listaUsers(){

        List<UserEntity> users = new ArrayList<>();
        Rol rol = new Rol(1L, ERol.ADMIN);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        UserEntity user1 = new UserEntity(1L, "Juan Perez", "prueba", "123", roles);

        Rol rol1 = new Rol(2L, ERol.USER);
        Set<Rol> roles1 = new HashSet<>();
        roles.add(rol1);
        UserEntity user2 = new UserEntity(2L, "Carlos Garcia", "garcia", "123", roles1);

        users.add(user1);
        users.add(user2);
        return users;
    }
}
