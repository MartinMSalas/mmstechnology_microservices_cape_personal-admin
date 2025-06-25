package com.mmstechnology.personal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SituacionLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean funcionario;
    private Boolean afectado;

    private String funcion;
    private String indice;

    private Boolean activo;
    private String razon;

    private String observaciones;

    private LocalDate fechaActualizacion;

    private String decreto;
    private String oficioTitulo;
    private String revista;
    private String agrupamiento;
    private String categoria;
    private String catSubroga;

    private String genero;
    private String instrumentoDeBaja;

    private String reparticion;
    private String secretaria;
    private String prestaServiciosEn;
    private String localidad;
    private String departamento;

    private String gde;
    private String area;
    private String reloj;

    @ManyToOne
    @JoinColumn(name = "personal_id", nullable = false)
    private Personal personal;
}
