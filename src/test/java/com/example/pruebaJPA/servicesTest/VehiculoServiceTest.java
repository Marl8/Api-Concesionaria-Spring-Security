package com.example.pruebaJPA.servicesTest;

import com.example.pruebaJPA.dto.VehiculoDto;
import com.example.pruebaJPA.dto.VehiculoGetDto;
import com.example.pruebaJPA.dto.VehiculoResponseDto;
import com.example.pruebaJPA.entity.Vehiculo;
import com.example.pruebaJPA.repository.IvehiculoRepository;
import com.example.pruebaJPA.service.VehiculoServiceImpl;
import com.example.pruebaJPA.utils.ObjectUtilsVehiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VehiculoServiceTest {

    @Mock
    IvehiculoRepository repository;

    @InjectMocks
    VehiculoServiceImpl service;

    @Test
    @DisplayName("Test OK para guardar vehículo")
    void guardarVehiculoOkTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // Arrange
        VehiculoDto vehiculoDto = ObjectUtilsVehiculo.vehiculoDto();
        List<Vehiculo> lista = ObjectUtilsVehiculo.listaVehiculos();
        Vehiculo vehiculo = ObjectUtilsVehiculo.vehiculo();
        VehiculoResponseDto expected = new VehiculoResponseDto("El vehiculo modelo uno se guardó correctamente.");
        Method metodoPrivado = VehiculoServiceImpl.class.getDeclaredMethod("verificarSiExiste", VehiculoDto.class);
        metodoPrivado.setAccessible(true);

        // Act
        when(metodoPrivado.invoke(service, vehiculoDto)).thenReturn(lista);
        when(repository.save(any())).thenReturn(vehiculo);
        VehiculoResponseDto actual = service.guardarVehiculo(vehiculoDto);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test OK para findAllSinServices")
    void findAllSinServicesOKTest() {

        List<Vehiculo> lista = ObjectUtilsVehiculo.listaVehiculos();
        List<VehiculoGetDto> expected = ObjectUtilsVehiculo.listaVehiculoGetDto();

        when(repository.findAll()).thenReturn(lista);
        List<VehiculoGetDto> actual = service.findAllSinServices();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test OK para findVehiculoById")
    void testFindVehiculoByIdOKTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        VehiculoDto expected = ObjectUtilsVehiculo.vehiculoDto();
        List<Vehiculo> argumentSut = ObjectUtilsVehiculo.listaVehiculos();
        Vehiculo vehiculo = ObjectUtilsVehiculo.vehiculo();
        Method metodoPrivado = VehiculoServiceImpl.class.getDeclaredMethod("verificarSiExisteId", int.class);
        metodoPrivado.setAccessible(true);

        when(metodoPrivado.invoke(service, 1)).thenReturn(argumentSut);
        when(repository.findById(any())).thenReturn(Optional.of(vehiculo));
        VehiculoDto actual = service.findVehiculoById(1);

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getModel(), actual.getModel()),
                () -> assertEquals(expected.getBrand(), actual.getBrand()),
                () -> assertEquals(expected.getManufacturingDate(), actual.getManufacturingDate())
        );
    }

    @Test
    @DisplayName("Test OK para findVehiculosByDate")
    void findVehiculosByDateOKTest(){

        List<VehiculoGetDto> expected = ObjectUtilsVehiculo.listaVehiculoGetDto();
        List<Vehiculo> lista = ObjectUtilsVehiculo.listaVehiculos();
        LocalDate fecha1 = LocalDate.parse("2017-06-19");
        LocalDate fecha2 = LocalDate.parse("2021-10-11");

        when(repository.findVehiculosByDateBetween(any(), any())).thenReturn(lista);
        List<VehiculoGetDto> actual = service.findVehiculosByDate(fecha1, fecha2);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test OK para findVehiculosByPrices")
    void findVehiculosByPricesOKTest(){

        List<VehiculoDto> expected = ObjectUtilsVehiculo.listaVehiculoDto();
        List<Vehiculo> lista = ObjectUtilsVehiculo.listaVehiculos();
        int price1 = 6000;
        int price2 = 12000;

        when(repository.findVehiculosByPriceBetween(price1, price2)).thenReturn(lista);
        List<VehiculoDto> actual = service.findVehiculosByPrice(price1, price2);

        assertAll(
                () -> assertEquals(expected.get(1).getManufacturingDate(), actual.get(1).getManufacturingDate()),
                () ->assertEquals(expected.get(0).getManufacturingDate(), actual.get(0).getManufacturingDate()),
                () -> assertEquals(expected.size(), actual.size())
        );
    }

    @Test
    @DisplayName("Test OK para modificarVehiculo")
    void modificarVehiculo(){

        VehiculoDto argumentSut = ObjectUtilsVehiculo.vehiculoDto();
        VehiculoResponseDto expected = new VehiculoResponseDto("Vehículo modificado con éxito");
        Optional<Vehiculo> vehiculo = ObjectUtilsVehiculo.vehiculoOptional();

        when(repository.findById(any())).thenReturn(vehiculo);
        VehiculoResponseDto actual = service.modificarVehiculo(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test OK para eliminarVehiculo")
    void eliminarVehiculo(){

        VehiculoResponseDto expected = new VehiculoResponseDto("El usuario fue eliminado con éxito");
        Optional<Vehiculo> argumentSut = ObjectUtilsVehiculo.vehiculoOptional();

        when(repository.findById(any())).thenReturn(argumentSut);
        VehiculoResponseDto actual = service.eliminarVehiculo(argumentSut.get().getId());

        assertEquals(expected, actual);
    }
}
