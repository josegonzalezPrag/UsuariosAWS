package com.pragma.usuariosaws.domain.ports.entrada;

import com.pragma.usuariosaws.domain.model.Persona;
import com.pragma.usuariosaws.infrastructura.dto.PersonarRequest;

public interface CrearPersonaPort {
    Persona crearPersona(PersonarRequest dto);
}