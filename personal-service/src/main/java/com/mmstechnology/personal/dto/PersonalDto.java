package com.mmstechnology.personal.dto;

import java.time.LocalDate;
import java.util.List;

public record PersonalDto(
    Long id,
    String cuil,
    String dni,
    String nombres,
    String apellidos,
    String legajo,
    LocalDate fechaNacimiento,
    LocalDate fechaIngreso,
    String ultimaActualizacionRealizadaPor,
    List<SituacionLaboralDto> situaciones
) {}
