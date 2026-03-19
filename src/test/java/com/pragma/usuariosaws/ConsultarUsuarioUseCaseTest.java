package com.pragma.usuariosaws;

import com.pragma.usuariosaws.aplication.usecases.ConsultarPersonaUseCase;
import com.pragma.usuariosaws.domain.model.Persona;
import com.pragma.usuariosaws.domain.ports.salida.PersonaRepositoryPort;
import com.pragma.usuariosaws.infrastructura.exception.PersonaNoFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarUsuarioUseCaseTest {
    @Mock
    private PersonaRepositoryPort repositoryPort;

    @InjectMocks
    private ConsultarPersonaUseCase consultarPersonaUseCase;

    private Persona persona;

    @BeforeEach
    void setUp() {
        persona = new Persona("1002656701", "Jose", "jose@gmail.com");
    }

    @Test
    void consultarPersona_exitoso() {
        when(repositoryPort.buscarPorId("1002656701")).thenReturn(Optional.of(persona));

        Persona resultado = consultarPersonaUseCase.consultarPersona("1002656701");

        assertNotNull(resultado);
        assertEquals("1002656701", resultado.getNumeroIdentificacion());
        assertEquals("Jose", resultado.getNombre());
        verify(repositoryPort, times(1)).buscarPorId("1002656701");
    }

    @Test
    void consultarPersona_lanzaExcepcion_cuandoNoExiste() {
        when(repositoryPort.buscarPorId("9999")).thenReturn(Optional.empty());

        assertThrows(PersonaNoFoundException.class,
                () -> consultarPersonaUseCase.consultarPersona("9999"));

        verify(repositoryPort, times(1)).buscarPorId("9999");
    }
}
