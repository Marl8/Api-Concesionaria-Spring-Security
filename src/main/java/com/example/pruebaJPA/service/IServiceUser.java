package com.example.pruebaJPA.service;

import com.example.pruebaJPA.dto.UserDto;
import com.example.pruebaJPA.dto.UserResponseDto;
import com.example.pruebaJPA.dto.UserSaveDto;
import com.example.pruebaJPA.dto.UserUpdateDto;
import java.util.List;

public interface IServiceUser {
    List<UserDto> getUsers();

    UserResponseDto save(UserSaveDto userDto);

    UserResponseDto update(UserUpdateDto userDto);

    UserResponseDto delete(Long id);
}
