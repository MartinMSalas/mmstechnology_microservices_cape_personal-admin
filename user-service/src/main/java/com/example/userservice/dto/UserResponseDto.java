package com.example.userservice.dto;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserResponseDto {
    private UUID id;
    private String nombreCompleto;
    private String email;
    private String rol;
    private Boolean estaActivo;
    private OffsetDateTime ultimoLoginEn;
    private OffsetDateTime creadoEn;
    private OffsetDateTime actualizadoEn;
}
