package com.example.pruebaJPA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorValidatorDto {

    private int status;
    private HashMap<String,String> errores;
}
