package com.example.pruebaJPA.dto;

import com.example.pruebaJPA.entity.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSaveDto {

    @NotBlank(message ="Debe indicarse el nombre del user")
    private String name;
    @NotBlank(message ="Debe indicarse el username del user")
    private String username;
    @NotBlank(message ="Debe indicarse el password del user")
    private String password;
    @NotEmpty(message = "El user debe tener asignado algún rol")
    private Set<Rol> roles;
}
