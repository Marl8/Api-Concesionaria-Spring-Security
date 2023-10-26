package com.example.pruebaJPA.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Vehiculo no encontrado")
public class VehiculoNotFoundException extends RuntimeException{

    public VehiculoNotFoundException(String message) {
        super(message);
    }
}
