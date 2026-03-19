package com.pragma.usuariosaws.infrastructura.exception.handler;

import com.pragma.usuariosaws.infrastructura.exception.EmailYaExisteException;
import com.pragma.usuariosaws.infrastructura.exception.PersonaNoFoundException;
import com.pragma.usuariosaws.infrastructura.exception.PersonaYaExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PersonaNoFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(PersonaNoFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PersonaYaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleYaExiste(PersonaYaExisteException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce("", (a, b) -> a + " | " + b);
        return buildResponse(HttpStatus.BAD_REQUEST, mensaje);
    }

    @ExceptionHandler(EmailYaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleEmailYaExiste(EmailYaExisteException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("mensaje", mensaje);
        return ResponseEntity.status(status).body(body);
    }
}
