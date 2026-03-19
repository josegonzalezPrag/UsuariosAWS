package com.pragma.usuariosaws.infrastructura.exception;

public class EmailYaExisteException extends RuntimeException {
    public EmailYaExisteException(String email) {
        super("Ya existe una persona con el email: " + email);
    }
}