package com.example.pruebaJPA.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
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

    @OneToMany(mappedBy = "vehiculo" ,cascade = CascadeType.ALL)
    private List<Service> services;

    @Positive
    @Min(1)
    private int countOfOwners;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return numberOfKilometers == vehiculo.numberOfKilometers && doors == vehiculo.doors && price == vehiculo.price && countOfOwners == vehiculo.countOfOwners && Objects.equals(brand, vehiculo.brand) && Objects.equals(model, vehiculo.model) && Objects.equals(manufacturingDate, vehiculo.manufacturingDate) && Objects.equals(currency, vehiculo.currency);
    }
}
