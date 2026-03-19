package com.pragma.usuariosaws.aplication.usecases;

import com.pragma.usuariosaws.domain.model.Persona;
import com.pragma.usuariosaws.domain.ports.entrada.ConsultarPersonaPort;
import com.pragma.usuariosaws.domain.ports.salida.PersonaRepositoryPort;
import com.pragma.usuariosaws.infrastructura.exception.PersonaNoFoundException;
import org.springframework.stereotype.Service;

@Service
public class ConsultarPersonaUseCase implements ConsultarPersonaPort {

    private final PersonaRepositoryPort repositoryPort;

    public ConsultarPersonaUseCase(PersonaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Persona consultarPersona(String numeroIdentificacion) {
        return repositoryPort.buscarPorId(numeroIdentificacion)
                .orElseThrow(() -> new PersonaNoFoundException(numeroIdentificacion));
    }
}