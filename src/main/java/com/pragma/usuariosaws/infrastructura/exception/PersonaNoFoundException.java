package com.pragma.usuariosaws.infrastructura.exception;

public class PersonaNoFoundException extends RuntimeException {
    public PersonaNoFoundException(String numeroIdentificacion) {
        super("No se encontró ninguna persona con identificación: " + numeroIdentificacion);
    }
}

