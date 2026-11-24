package ar.edu.siglo21.socioclub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ar.edu.siglo21.socioclub.dto.ErrorDTO;
import ar.edu.siglo21.socioclub.service.RecursoNoEncontradoException;
import ar.edu.siglo21.socioclub.service.ReglaNegocioException;

/**
 * Convierte las excepciones del servicio en respuestas HTTP con JSON.
 * Solo se aplica a los controladores marcados con @RestController (la API);
 * las vistas Thymeleaf manejan sus errores mostrando el mensaje en la pantalla.
 */
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    // 404 Not Found: el socio, la actividad o la inscripción no existen
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorDTO> noEncontrado(RecursoNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(e.getMessage(), null));
    }

    // 409 Conflict: una regla de negocio rechazó la operación. Datos mal cargados -> 400 Bad Request
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorDTO> reglaNoCumplida(ReglaNegocioException e) {
        HttpStatus estado = "DATOS".equals(e.getRegla()) ? HttpStatus.BAD_REQUEST : HttpStatus.CONFLICT;
        return ResponseEntity.status(estado).body(new ErrorDTO(e.getMessage(), e.getRegla()));
    }
}
