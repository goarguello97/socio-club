package ar.edu.siglo21.socioclub.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.siglo21.socioclub.dto.ActividadDTO;
import ar.edu.siglo21.socioclub.model.Actividad;
import ar.edu.siglo21.socioclub.service.ActividadService;

/**
 * API REST de actividades. Usa el mismo ActividadService que el sistema de secretaría.
 * Los errores (404 y 409) los arma ApiExceptionHandler.
 */
@RestController
@RequestMapping("/api/actividades")
public class ActividadRestController {

    private final ActividadService actividadService;

    public ActividadRestController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    // GET /api/actividades?disciplina=Fútbol -> 200 con la lista y el cupo disponible de cada una
    @GetMapping
    public List<ActividadDTO> listar(@RequestParam(required = false) String disciplina) {
        return actividadService.listar(disciplina).stream().map(ActividadDTO::desde).toList();
    }

    // GET /api/actividades/1 -> 200, o 404 si no existe
    @GetMapping("/{id}")
    public ActividadDTO buscar(@PathVariable Long id) {
        return ActividadDTO.desde(actividadService.buscarPorId(id));
    }

    // POST /api/actividades -> 201 Created con la actividad creada y su URL en el encabezado Location
    @PostMapping
    public ResponseEntity<ActividadDTO> crear(@RequestBody ActividadDTO datos) {
        Actividad nueva = datos.aEntidad();
        nueva.setId(null);
        ActividadDTO creada = ActividadDTO.desde(actividadService.guardar(nueva));
        return ResponseEntity.created(URI.create("/api/actividades/" + creada.getId())).body(creada);
    }

    // PUT /api/actividades/1 -> 200 con la actividad modificada, o 404 si no existe
    @PutMapping("/{id}")
    public ActividadDTO modificar(@PathVariable Long id, @RequestBody ActividadDTO datos) {
        Actividad cambios = datos.aEntidad();
        cambios.setId(id);
        return ActividadDTO.desde(actividadService.guardar(cambios));
    }

    // DELETE /api/actividades/1 -> 204 sin cuerpo; 404 si no existe; 409 si tiene inscriptos activos
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        actividadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
