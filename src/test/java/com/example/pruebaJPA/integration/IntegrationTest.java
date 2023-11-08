package com.example.pruebaJPA.integration;


import com.example.pruebaJPA.dto.VehiculoDto;
import com.example.pruebaJPA.entity.UserEntity;
import com.example.pruebaJPA.service.securityServices.jwt.JwtService;
import com.example.pruebaJPA.utils.ObjectUtils;
import com.example.pruebaJPA.utils.ObjectUtilsVehiculo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    @Test
    void findAllSinServicesOktest() throws Exception {

        UserEntity user = ObjectUtils.user();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("roles", user.getRoles().stream()
                .map(rol -> rol.getName().name()).toList());
        String token = jwtService.generate(user,extraClaims);

        MvcResult response = mockMvc.perform(get( "/v1/api/vehicles")
                        .header("Authorization", "Bearer "+token))
                .andDo(print()) // imprime por consola el request y él response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].brand").value("Peugeot"))
                .andReturn();

        // verifica si retorna un json
        assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void findByIdOkTest() throws Exception {

        UserEntity user = ObjectUtils.user();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("roles", user.getRoles().stream()
                .map(rol -> rol.getName().name()).toList());
        String token = jwtService.generate(user,extraClaims);

        mockMvc.perform(get( "/v1/api/vehicles/{id}", 1)
                        .header("Authorization", "Bearer " + token))
                .andDo(print()) // imprime por consola el request y él response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Fiat"));
    }

    @Test
    void mostrarVehiculoNotFoundTest() throws Exception {

        UserEntity user = ObjectUtils.user();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("roles", user.getRoles().stream()
                .map(rol -> rol.getName().name()).toList());
        String token = jwtService.generate(user,extraClaims);

        mockMvc.perform(get( "/v1/api/vehicles/{id}", 15)
                        .header("Authorization", "Bearer " + token))
                .andDo(print()) // imprime por consola el request y él response
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.mensaje").value("No existen vehículos con este Id"));
    }

    @Test
    void findByIdNotFoundTestParam() throws Exception {

        UserEntity user = ObjectUtils.user();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("roles", user.getRoles().stream()
                .map(rol -> rol.getName().name()).toList());
        String token = jwtService.generate(user,extraClaims);

        mockMvc.perform(get( "/v1/api/vehicles/prices")
                        .param("since", "20000")
                        .param("to", "30000")
                        .header("Authorization", "Bearer " + token))
        .andDo(print()) // imprime por consola el request y él response
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value("404"))
        .andExpect(jsonPath("$.mensaje").value("No se encontraron vehículos en el rango seleccionado"));
    }

    @Test
    void vehiculoGuardarOKTest() throws Exception{

        UserEntity user = ObjectUtils.user();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("roles", user.getRoles().stream()
                .map(rol -> rol.getName().name()).toList());
        String token = jwtService.generate(user,extraClaims);

        VehiculoDto dto = ObjectUtilsVehiculo.objetoVehiculoIntegracion();

        ObjectMapper obMapper = new ObjectMapper();
        obMapper.registerModule(new JavaTimeModule());
        ObjectWriter mapper = obMapper
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String payload = mapper.writeValueAsString(dto);


        mockMvc.perform(post( "/v1/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .header("Authorization", "Bearer " + token))
                .andDo(print()) // imprime por consola el request y él response
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El vehiculo modelo Amarok se guardó correctamente."));
    }
}
