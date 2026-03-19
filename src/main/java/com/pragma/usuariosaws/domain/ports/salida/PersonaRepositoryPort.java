package com.pragma.usuariosaws.domain.ports.salida;

import com.pragma.usuariosaws.domain.model.Persona;

import java.util.Optional;

public interface PersonaRepositoryPort {
    boolean existePorId(String numeroIdentificacion);
    boolean existePorEmail(String email);
    Persona guardar(Persona persona);
    Optional<Persona> buscarPorId(String numeroIdentificacion);
}
