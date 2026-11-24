package ar.edu.siglo21.socioclub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.siglo21.socioclub.dto.InscripcionDTO;
import ar.edu.siglo21.socioclub.dto.SocioDTO;
import ar.edu.siglo21.socioclub.service.InscripcionService;
import ar.edu.siglo21.socioclub.service.SocioService;

/** API REST de socios: ingreso a la app por DNI y consulta de sus inscripciones. */
@RestController
@RequestMapping("/api/socios")
public class SocioRestController {

    private final SocioService socioService;
    private final InscripcionService inscripcionService;

    public SocioRestController(SocioService socioService, InscripcionService inscripcionService) {
        this.socioService = socioService;
        this.inscripcionService = inscripcionService;
    }

    // GET /api/socios/dni/31456789 -> 200 con el socio, o 404 si no hay ninguno con ese DNI
    @GetMapping("/dni/{dni}")
    public SocioDTO buscarPorDni(@PathVariable String dni) {
        return SocioDTO.desde(socioService.buscarPorDni(dni));
    }

    // GET /api/socios/1/inscripciones -> 200 con las inscripciones ACTIVAS, o 404 si el socio no existe
    @GetMapping("/{id}/inscripciones")
    public List<InscripcionDTO> inscripciones(@PathVariable Long id) {
        return inscripcionService.listarActivasDeSocio(id).stream().map(InscripcionDTO::desde).toList();
    }
}
