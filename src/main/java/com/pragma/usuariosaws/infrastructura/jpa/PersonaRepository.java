package com.pragma.usuariosaws.infrastructura.jpa;

import com.pragma.usuariosaws.domain.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {
    boolean existsByEmail(String email);
}
