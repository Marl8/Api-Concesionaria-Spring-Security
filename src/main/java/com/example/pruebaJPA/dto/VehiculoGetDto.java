package com.example.pruebaJPA.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class VehiculoGetDto {

    @Min(1)
    private Long id;
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @NotNull
    private LocalDate manufacturingDate;
    @Positive
    @Min(1)
    private int numberOfKilometers;
    @Min(2)
    private int doors;
    @Positive
    @Min(1000)
    private int price;
    @NotBlank
    private String currency;
    @Min(1)
    private int countOfOwners;
}
