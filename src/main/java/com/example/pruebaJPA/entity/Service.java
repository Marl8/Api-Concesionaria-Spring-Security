package com.example.pruebaJPA.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "services")
public class Service {

    @Id
    @Column(name = "id_service")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idService;

    @NotBlank
    private String date;
    private String description;

    @Min(1)
    private int kilometers;

    @ManyToOne
    @JoinColumn(name = "fk_vehiculo")
    @JsonIgnore // Evita la recursividad en las relaciones Bidireccionales
    private Vehiculo vehiculo;
}
