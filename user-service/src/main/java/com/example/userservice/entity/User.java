package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios") // Matches schema.sql
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Or GenerationType.UUID if your DB/JPA setup supports it directly
    private UUID id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "rol", nullable = false)
    private String rol; // Could be an Enum if roles are fixed

    @Column(name = "esta_activo", nullable = false)
    private Boolean estaActivo;

    @Column(name = "ultimo_login_en")
    private OffsetDateTime ultimoLoginEn; // Matches TIMESTAMP WITH TIME ZONE

    @Column(name = "creado_en", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        creadoEn = now;
        actualizadoEn = now;
        if (estaActivo == null) {
            estaActivo = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = OffsetDateTime.now();
    }
}
