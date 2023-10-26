package com.example.pruebaJPA.controller;

import com.example.pruebaJPA.dto.VehiculoDto;
import com.example.pruebaJPA.service.IvehiculoService;
import com.example.pruebaJPA.service.VehiculoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/v1/api/vehicles")
public class VehiculoController {

    IvehiculoService service;

    public VehiculoController(VehiculoServiceImpl vehiculoService) {
        this.service = vehiculoService;
    }

    @PostMapping("/")
    public ResponseEntity<?> guardarVehiculo(@Valid @RequestBody VehiculoDto vehiculoDto){
        return new ResponseEntity<>(service.guardarVehiculo(vehiculoDto), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllSinServices(){
        return new ResponseEntity<>(service.findAllSinServices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findVehiculoById(@Valid @PathVariable int id){
        return new ResponseEntity<>(service.findVehiculoById(id), HttpStatus.OK);
    }

    @GetMapping("/dates")
    public ResponseEntity<?> findVehiculoByDate(
            @Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate since,
            @Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to){
        return new ResponseEntity<>(service.findVehiculosByDate(since, to), HttpStatus.OK);
    }

    @GetMapping("/prices")
    public ResponseEntity<?> findVehiculoByPrices(@Valid @RequestParam int since, @Valid @RequestParam int to){
        return new ResponseEntity<>(service.findVehiculosByPrice(since, to), HttpStatus.OK);
    }
}
