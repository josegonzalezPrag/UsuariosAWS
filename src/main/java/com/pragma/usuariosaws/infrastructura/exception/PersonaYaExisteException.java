package com.pragma.usuariosaws.infrastructura.exception;

public class PersonaYaExisteException extends RuntimeException {
    public PersonaYaExisteException(String numeroIdentificacion) {
        super("Ya existe una persona con identificación: " + numeroIdentificacion);
    }
}