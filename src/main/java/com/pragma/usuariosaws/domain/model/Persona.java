package com.pragma.usuariosaws.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "persona")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Persona {
    @Id
    @Column(name = "numero_identificacion", nullable = false, unique = true)
    private String numeroIdentificacion;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;
}
