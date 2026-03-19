package com.pragma.usuariosaws.adapter;

import com.pragma.usuariosaws.domain.model.Persona;
import com.pragma.usuariosaws.domain.ports.salida.PersonaRepositoryPort;
import com.pragma.usuariosaws.infrastructura.jpa.PersonaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonaRepositoryAdapter implements PersonaRepositoryPort {
    private final PersonaRepository repository;

    public PersonaRepositoryAdapter(PersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existePorId(String numeroIdentificacion) {
        return repository.existsById(numeroIdentificacion);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Persona guardar(Persona persona) {
        return repository.save(persona);
    }

    @Override
    public Optional<Persona> buscarPorId(String numeroIdentificacion) {
        return repository.findById(numeroIdentificacion);
    }
}
