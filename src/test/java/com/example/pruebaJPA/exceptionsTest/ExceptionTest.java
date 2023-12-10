package com.example.pruebaJPA.exceptionsTest;

import com.example.pruebaJPA.dto.ErrorDto;
import com.example.pruebaJPA.exception.ExceptionController;
import com.example.pruebaJPA.exception.VehiculoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExceptionTest {

    @Autowired
    ExceptionController controller;

    @Test
    void vehiculoNotFoundExceptionTest(){

        // Arrange
        VehiculoNotFoundException argumentSut = new VehiculoNotFoundException("Excepción lanzada");
        ErrorDto error = new ErrorDto(404, "Excepción lanzada");
        ResponseEntity<?> expected = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        // Act
        ResponseEntity<?> actual = controller.vehiculoNotFound(argumentSut);

        // Assert
        assertEquals(expected, actual);
    }
}
