package com.mmstechnology.personal.dto;

import java.time.LocalDate;

public record SituacionLaboralDto(
    Long id,
    Boolean funcionario,
    Boolean afectado,
    String funcion,
    String indice,
    Boolean activo,
    String razon,
    String observaciones,
    LocalDate fechaActualizacion,
    String decreto,
    String oficioTitulo,
    String revista,
    String agrupamiento,
    String categoria,
    String catSubroga,
    String genero,
    String instrumentoDeBaja,
    String reparticion,
    String secretaria,
    String prestaServiciosEn,
    String localidad,
    String departamento,
    String gde,
    String area,
    String reloj
) {}
