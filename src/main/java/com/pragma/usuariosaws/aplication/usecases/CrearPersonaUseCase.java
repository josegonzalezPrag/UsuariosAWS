package com.pragma.usuariosaws.aplication.usecases;

import com.pragma.usuariosaws.domain.model.Persona;
import com.pragma.usuariosaws.domain.ports.entrada.CrearPersonaPort;
import com.pragma.usuariosaws.domain.ports.salida.PersonaRepositoryPort;
import com.pragma.usuariosaws.infrastructura.dto.PersonarRequest;
import com.pragma.usuariosaws.infrastructura.exception.EmailYaExisteException;
import com.pragma.usuariosaws.infrastructura.exception.PersonaYaExisteException;
import org.springframework.stereotype.Service;

@Service
public class CrearPersonaUseCase implements CrearPersonaPort {

    private final PersonaRepositoryPort repositoryPort;

    public CrearPersonaUseCase(PersonaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Persona crearPersona(PersonarRequest dto) {
        if (repositoryPort.existePorId(dto.getNumeroIdentificacion())) {
            throw new PersonaYaExisteException(dto.getNumeroIdentificacion());
        }
        if (repositoryPort.existePorEmail(dto.getEmail())) {
            throw new EmailYaExisteException(dto.getEmail());
        }
        Persona persona = new Persona(
                dto.getNumeroIdentificacion(),
                dto.getNombre(),
                dto.getEmail()
        );
        return repositoryPort.guardar(persona);
    }
}