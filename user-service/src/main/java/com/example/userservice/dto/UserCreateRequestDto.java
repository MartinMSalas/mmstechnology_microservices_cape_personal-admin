package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequestDto {
    @NotEmpty(message = "Full name cannot be empty")
    private String nombreCompleto;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotEmpty(message = "Role cannot be empty")
    private String rol; // e.g., "USUARIO", "ADMINISTRADOR"

    private Boolean estaActivo = true;
}
