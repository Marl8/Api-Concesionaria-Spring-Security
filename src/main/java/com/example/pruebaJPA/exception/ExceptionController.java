package com.example.pruebaJPA.exception;

import com.example.pruebaJPA.dto.ErrorDto;
import com.example.pruebaJPA.dto.ErrorValidatorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(VehiculoNotFoundException.class)
    public ResponseEntity<?> vehiculoNotFound(VehiculoNotFoundException ex){

        ErrorDto error = new ErrorDto(404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VehiculoClonException.class)
    public ResponseEntity<?> vehiculoClon(VehiculoClonException ex){

        ErrorDto error = new ErrorDto(400, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehiculoNoSaveException.class)
    public ResponseEntity<?> vehiculoNoGuardado(VehiculoNoSaveException ex){

        ErrorDto error = new ErrorDto(400, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehiculoNotFoundIdException.class)
    public ResponseEntity<?> vehiculoFoundId(VehiculoNotFoundIdException ex){

        ErrorDto error = new ErrorDto(404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserGenericException.class)
    public ResponseEntity<?> userGeneric(UserGenericException ex){

        ErrorDto error = new ErrorDto(404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> fallaValidacion(MethodArgumentNotValidException ex){

        // Mapa para mostrar todos los errores cuando ocurren varios juntos.
        HashMap<String, String> errores = new HashMap<>();
        ex.getFieldErrors()
                .forEach(field -> errores.put(field.getField(), field.getDefaultMessage()));

        /*
        GetDefaultMessage extrae el mensaje default establecido en la validación de toda la Field Error
        ErrorDto error = new ErrorDto(400, ex.getFieldError().getDefaultMessage());
        */
        return new ResponseEntity<>(new ErrorValidatorDto(400, errores), HttpStatus.BAD_REQUEST);
    }
}
