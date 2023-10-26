package com.example.pruebaJPA.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {

    @NotBlank(message = "Se requiere un username")
    private String username;
    @NotBlank(message = "Se requiere un password")
    private String password;
}
