package com.pragma.usuariosaws.infrastructura.controllers;

import com.pragma.usuariosaws.domain.ports.entrada.ConsultarPersonaPort;
import com.pragma.usuariosaws.domain.ports.entrada.CrearPersonaPort;
import com.pragma.usuariosaws.infrastructura.dto.PersonarRequest;
import com.pragma.usuariosaws.domain.model.Persona;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personas")
@Tag(name = "Personas", description = "Endpoints para gestión de personas")
public class PersonaController {
    private final CrearPersonaPort crearPersonaPort;
    private final ConsultarPersonaPort consultarPersonaPort;

    public PersonaController(CrearPersonaPort crearPersonaPort,
                             ConsultarPersonaPort consultarPersonaPort) {
        this.crearPersonaPort = crearPersonaPort;
        this.consultarPersonaPort = consultarPersonaPort;
    }
    @Operation(summary = "Crear una persona", description = "Registra una nueva persona en el sistema")
    @ApiResponse(responseCode = "201", description = "Persona creada exitosamente",
                 content = @Content(schema = @Schema(implementation = Persona.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    @ApiResponse(responseCode = "409", description = "La persona ya existe", content = @Content)
    @PostMapping
    public ResponseEntity<Persona> crearPersona(@Valid @RequestBody PersonarRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearPersonaPort.crearPersona(dto));
    }

    @Operation(summary = "Consultar una persona", description = "Busca una persona por número de identificación")
    @ApiResponse(responseCode = "200", description = "Persona encontrada",
                content = @Content(schema = @Schema(implementation = Persona.class)))
    @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content)
    @GetMapping("/{numeroIdentificacion}")
    public ResponseEntity<Persona> consultarPersona(@PathVariable String numeroIdentificacion) {
        return ResponseEntity.ok(consultarPersonaPort.consultarPersona(numeroIdentificacion));
    }
}
