package com.mmstechnology.personal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String cuil;

    @NotBlank
    private String dni;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @NotBlank
    private String legajo;

    @Past
    private LocalDate fechaNacimiento;

    @Past
    private LocalDate fechaIngreso;

    @CreatedDate
    private LocalDateTime fechaUltimaActualizacion;

    @NotBlank
    private String ultimaActualizacionRealizadaPor;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SituacionLaboral> situaciones;
}
