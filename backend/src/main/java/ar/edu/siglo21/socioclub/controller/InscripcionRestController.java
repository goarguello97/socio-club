package ar.edu.siglo21.socioclub.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.siglo21.socioclub.dto.InscripcionDTO;
import ar.edu.siglo21.socioclub.dto.InscripcionRequest;
import ar.edu.siglo21.socioclub.service.InscripcionService;
import ar.edu.siglo21.socioclub.service.ReglaNegocioException;

/** API REST de inscripciones. Las reglas RN1 a RN5 las aplica InscripcionService. */
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionRestController {

    private final InscripcionService inscripcionService;

    public InscripcionRestController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    // POST /api/inscripciones con { "socioId": 3, "actividadId": 1 }
    // -> 201 si se inscribió; 404 si no existe el socio o la actividad; 409 si una regla lo rechaza
    @PostMapping
    public ResponseEntity<InscripcionDTO> inscribir(@RequestBody InscripcionRequest pedido) {
        if (pedido.getSocioId() == null || pedido.getActividadId() == null) {
            throw new ReglaNegocioException("DATOS", "Hay que indicar socioId y actividadId");
        }
        InscripcionDTO creada = InscripcionDTO.desde(
                inscripcionService.inscribir(pedido.getSocioId(), pedido.getActividadId()));
        return ResponseEntity.created(URI.create("/api/inscripciones/" + creada.getId())).body(creada);
    }

    // PUT /api/inscripciones/7/baja -> 200 con la inscripción en estado BAJA, o 404 si no existe
    @PutMapping("/{id}/baja")
    public InscripcionDTO darDeBaja(@PathVariable Long id) {
        return InscripcionDTO.desde(inscripcionService.darDeBaja(id));
    }
}
