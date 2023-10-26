package com.example.pruebaJPA.service;

import com.example.pruebaJPA.dto.VehiculoDto;
import com.example.pruebaJPA.dto.VehiculoGetDto;
import com.example.pruebaJPA.dto.VehiculoResponseDto;
import com.example.pruebaJPA.exception.VehiculoNotFoundIdException;

import java.time.LocalDate;
import java.util.List;

public interface IvehiculoService {

    public VehiculoResponseDto guardarVehiculo(VehiculoDto auto);

    public List<VehiculoGetDto> findAllSinServices();

    public VehiculoDto findVehiculoById(int id) throws VehiculoNotFoundIdException;

    public List<VehiculoGetDto> findVehiculosByDate(LocalDate date1, LocalDate date2);

    public List<VehiculoDto> findVehiculosByPrice(int price1, int price2);
}
