package com.pragma.usuariosaws;

import com.pragma.usuariosaws.aplication.usecases.CrearPersonaUseCase;
import com.pragma.usuariosaws.domain.model.Persona;
import com.pragma.usuariosaws.domain.ports.salida.PersonaRepositoryPort;
import com.pragma.usuariosaws.infrastructura.dto.PersonarRequest;
import com.pragma.usuariosaws.infrastructura.exception.EmailYaExisteException;
import com.pragma.usuariosaws.infrastructura.exception.PersonaYaExisteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearPersonaUseCaseTest {
    @Mock
    private PersonaRepositoryPort repositoryPort;

    @InjectMocks
    private CrearPersonaUseCase crearPersonaUseCase;

    private PersonarRequest dto;
    private Persona persona;

    @BeforeEach
    void setUp() {
        dto = new PersonarRequest();
        dto.setNumeroIdentificacion("1002656701");
        dto.setNombre("Jose");
        dto.setEmail("jose@gmail.com");

        persona = new Persona("1002656701", "Jose", "jose@gmail.com");
    }

    @Test
    void crearPersona_exitoso() {
        when(repositoryPort.existePorId(dto.getNumeroIdentificacion())).thenReturn(false);
        when(repositoryPort.existePorEmail(dto.getEmail())).thenReturn(false);
        when(repositoryPort.guardar(any(Persona.class))).thenReturn(persona);

        Persona resultado = crearPersonaUseCase.crearPersona(dto);

        assertNotNull(resultado);
        assertEquals("1002656701", resultado.getNumeroIdentificacion());
        assertEquals("Jose", resultado.getNombre());
        assertEquals("jose@gmail.com", resultado.getEmail());
        verify(repositoryPort, times(1)).guardar(any(Persona.class));
    }

    @Test
    void crearPersona_lanzaExcepcion_cuandoIdYaExiste() {
        when(repositoryPort.existePorId(dto.getNumeroIdentificacion())).thenReturn(true);

        assertThrows(PersonaYaExisteException.class,
                () -> crearPersonaUseCase.crearPersona(dto));

        verify(repositoryPort, never()).guardar(any());
    }

    @Test
    void crearPersona_lanzaExcepcion_cuandoEmailYaExiste() {
        when(repositoryPort.existePorId(dto.getNumeroIdentificacion())).thenReturn(false);
        when(repositoryPort.existePorEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailYaExisteException.class,
                () -> crearPersonaUseCase.crearPersona(dto));

        verify(repositoryPort, never()).guardar(any());
    }
}
