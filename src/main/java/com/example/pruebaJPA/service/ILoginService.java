package com.example.pruebaJPA.service;

import com.example.pruebaJPA.dto.JwtDto;
import com.example.pruebaJPA.dto.LoginDto;

public interface ILoginService {


    JwtDto login(LoginDto loginDto);
}
