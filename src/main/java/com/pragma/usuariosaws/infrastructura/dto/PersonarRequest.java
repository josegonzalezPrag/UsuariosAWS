package com.pragma.usuariosaws.infrastructura.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonarRequest {
    @NotBlank(message = "El número de identificación es obligatorio")
    private String numeroIdentificacion;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
}
