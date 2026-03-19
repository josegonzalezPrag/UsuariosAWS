package com.pragma.usuariosaws;

import com.pragma.usuariosaws.domain.model.Persona;
import com.pragma.usuariosaws.domain.ports.entrada.ConsultarPersonaPort;
import com.pragma.usuariosaws.domain.ports.entrada.CrearPersonaPort;
import com.pragma.usuariosaws.infrastructura.controllers.PersonaController;
import com.pragma.usuariosaws.infrastructura.dto.PersonarRequest;
import com.pragma.usuariosaws.infrastructura.exception.PersonaNoFoundException;
import com.pragma.usuariosaws.infrastructura.exception.PersonaYaExisteException;
import com.pragma.usuariosaws.infrastructura.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tools.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CrearPersonaPort crearPersonaPort;

    @Mock
    private ConsultarPersonaPort consultarPersonaPort;

    @InjectMocks
    private PersonaController personaController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Persona persona;
    private PersonarRequest dto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(personaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        persona = new Persona("1002656701", "Jose", "jose@gmail.com");

        dto = new PersonarRequest();
        dto.setNumeroIdentificacion("1002656701");
        dto.setNombre("Jose");
        dto.setEmail("jose@gmail.com");
    }

    @Test
    void crearPersona_retorna201() throws Exception {
        when(crearPersonaPort.crearPersona(any(PersonarRequest.class))).thenReturn(persona);

        mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroIdentificacion").value("1002656701"))
                .andExpect(jsonPath("$.nombre").value("Jose"))
                .andExpect(jsonPath("$.email").value("jose@gmail.com"));
    }

    @Test
    void crearPersona_retorna409_cuandoYaExiste() throws Exception {
        when(crearPersonaPort.crearPersona(any(PersonarRequest.class)))
                .thenThrow(new PersonaYaExisteException("1002656701"));

        mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    void crearPersona_retorna400_cuandoDatosInvalidos() throws Exception {
        PersonarRequest dtoInvalido = new PersonarRequest();
        dtoInvalido.setNumeroIdentificacion("");
        dtoInvalido.setNombre("");
        dtoInvalido.setEmail("no-es-un-email");

        mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void consultarPersona_retorna200() throws Exception {
        when(consultarPersonaPort.consultarPersona("1002656701")).thenReturn(persona);

        mockMvc.perform(get("/api/personas/1002656701"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroIdentificacion").value("1002656701"))
                .andExpect(jsonPath("$.nombre").value("Jose"));
    }

    @Test
    void consultarPersona_retorna404_cuandoNoExiste() throws Exception {
        when(consultarPersonaPort.consultarPersona("9999"))
                .thenThrow(new PersonaNoFoundException("9999"));

        mockMvc.perform(get("/api/personas/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
