package com.example.pruebaJPA.utils;

import com.example.pruebaJPA.dto.VehiculoDto;
import com.example.pruebaJPA.dto.VehiculoGetDto;
import com.example.pruebaJPA.entity.Service;
import com.example.pruebaJPA.entity.Vehiculo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjectUtilsVehiculo {

    public static VehiculoDto vehiculoDto(){

        List<Service> servicios = new ArrayList<>();
        LocalDate fechaFabricacion = LocalDate.parse("2007-02-26");
        VehiculoDto vehiculodto = new VehiculoDto(1L,"fiat", "uno", fechaFabricacion, 102500,
                3, 3500, "AR", servicios  , 6);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Vehiculo vehiculo = mapper.convertValue(vehiculodto, Vehiculo.class);
        servicios.add(new Service(1L, "2007-02-26", "filters", 10000, vehiculo));
        vehiculodto.setServices(servicios);
        return vehiculodto;
    }

    public static Vehiculo vehiculo() {

        List<Service> servicios = new ArrayList<>();
        LocalDate fechaFabricacion = LocalDate.parse("2007-02-26");
        Vehiculo vehiculo = new Vehiculo(1L,"fiat", "uno", fechaFabricacion, 102500,
                3, 3500, "AR", servicios  , 6);
        servicios.add(new Service(1L, "2007-02-26", "filters", 10000, vehiculo));
        vehiculo.setServices(servicios);
        return vehiculo;
    }

    public static Optional<Vehiculo> vehiculoOptional() {

        List<Service> servicios = new ArrayList<>();
        LocalDate fechaFabricacion = LocalDate.parse("2007-02-26");
        Vehiculo vehiculo = new Vehiculo(1L,"fiat", "uno", fechaFabricacion, 102500,
                3, 3500, "AR", servicios  , 6);
        servicios.add(new Service(1L, "2007-02-26", "filters", 10000, vehiculo));
        vehiculo.setServices(servicios);

        Optional<Vehiculo> op = Optional.of(vehiculo);
        return op;
    }

    public static List<Vehiculo> listaVehiculos() {
        List<Vehiculo> traerTodos = new ArrayList<>();
        List<Service> servicios1 = new ArrayList<>();
        LocalDate fechaFabricacion = LocalDate.parse("2019-06-16");
        Vehiculo vehiculo1 = new Vehiculo(1L,"renault", "logan", fechaFabricacion, 7500,
                4, 9500, "AR", servicios1  , 3);
        servicios1.add(new Service(1L, "2020-04-16", "filters", 10000, vehiculo1));
        vehiculo1.setServices(servicios1);

        List<Service> servicios2 = new ArrayList<>();
        LocalDate fechaFabricacion1 = LocalDate.parse("2010-02-26");
        Vehiculo vehiculo2 = new Vehiculo(2L,"ford", "fiesta", fechaFabricacion1, 92700,
                3, 4900, "AR", servicios2  , 4);
        servicios2.add(new Service(2L, "2013-02-26", "filters", 10000, vehiculo2));
        vehiculo2.setServices(servicios2);

        traerTodos.add(vehiculo1);
        traerTodos.add(vehiculo2);
        return traerTodos;
    }

    public static List<VehiculoDto> listaVehiculoDto(){
        List<Service> servicios1 = new ArrayList<>();
        LocalDate fechaFabricacion = LocalDate.parse("2019-06-16");
        VehiculoDto vehiculodto = new VehiculoDto(1L,"renault", "logan", fechaFabricacion, 7500,
                4, 9500, "AR", servicios1  , 3);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Vehiculo vehiculo1 = mapper.convertValue(vehiculodto, Vehiculo.class);
        servicios1.add(new Service(1L, "2020-04-16", "filters", 10000, vehiculo1));

        vehiculodto.setServices(servicios1);

        List<Service> servicios2 = new ArrayList<>();
        LocalDate fechaFabricacion1 = LocalDate.parse("2010-02-26");
        VehiculoDto vehiculodto1 = new VehiculoDto(2L,"ford", "fiesta", fechaFabricacion1, 92700,
                3, 4900, "AR", servicios2  , 4);

        Vehiculo vehiculo2 = mapper.convertValue(vehiculodto1, Vehiculo.class);
        servicios2.add(new Service(2L, "2013-02-26", "filters", 10000, vehiculo2));
        vehiculodto1.setServices(servicios2);

        List<VehiculoDto> dtoList = new ArrayList<>();
        dtoList.add(vehiculodto);
        dtoList.add(vehiculodto1);
        return dtoList;
    }

    public static List<VehiculoGetDto> listaVehiculoGetDto() {
        List<VehiculoGetDto> traerTodosSinServices = new ArrayList<>();
        LocalDate fechaFabricacion = LocalDate.parse("2019-06-16");
        VehiculoGetDto vehiculo1 = new VehiculoGetDto(1L,"renault", "logan", fechaFabricacion, 7500,
                4, 9500, "AR" , 3);

        LocalDate fechaFabricacion1 = LocalDate.parse("2010-02-26");
        VehiculoGetDto vehiculo2 = new VehiculoGetDto(2L,"ford", "fiesta", fechaFabricacion1, 92700,
                3, 4900, "AR", 4);

        traerTodosSinServices.add(vehiculo1);
        traerTodosSinServices.add(vehiculo2);
        return traerTodosSinServices;
    }
}
