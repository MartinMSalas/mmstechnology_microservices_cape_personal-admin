package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateRequestDto {
    private String nombreCompleto;

    @Email(message = "Email should be valid if provided")
    private String email; // Optional update

    private String password; // Optional update, if provided, must meet criteria

    private String rol;

    private Boolean estaActivo;
}
