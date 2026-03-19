package com.pragma.usuariosaws.domain.ports.entrada;

import com.pragma.usuariosaws.domain.model.Persona;

public interface ConsultarPersonaPort {
    Persona consultarPersona(String numeroIdentificacion);
}