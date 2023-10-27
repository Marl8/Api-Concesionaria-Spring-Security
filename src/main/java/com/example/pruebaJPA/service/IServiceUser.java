package com.example.pruebaJPA.service;

import com.example.pruebaJPA.dto.UserDto;
import com.example.pruebaJPA.dto.UserResponseDto;
import com.example.pruebaJPA.dto.UserSaveDto;
import com.example.pruebaJPA.dto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServiceUser {
    Page<UserDto> getUsers(Pageable pagination);

    UserResponseDto save(UserSaveDto userDto);

    UserResponseDto update(UserUpdateDto userDto);

    UserResponseDto delete(Long id);
}
