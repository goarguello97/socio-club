package ar.edu.siglo21.socioclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.siglo21.socioclub.model.Inscripcion;
import ar.edu.siglo21.socioclub.service.ActividadService;
import ar.edu.siglo21.socioclub.service.InscripcionService;
import ar.edu.siglo21.socioclub.service.RecursoNoEncontradoException;
import ar.edu.siglo21.socioclub.service.ReglaNegocioException;
import ar.edu.siglo21.socioclub.service.SocioService;

/**
 * Inscripciones desde el sistema de secretaría. No valida nada por su cuenta:
 * las reglas RN1 a RN5 las aplica InscripcionService, igual que para la API REST.
 */
@Controller
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final SocioService socioService;
    private final ActividadService actividadService;

    public InscripcionController(InscripcionService inscripcionService, SocioService socioService,
                                 ActividadService actividadService) {
        this.inscripcionService = inscripcionService;
        this.socioService = socioService;
        this.actividadService = actividadService;
    }

    // GET /inscripciones/nueva?socioId=3&actividadId=1 (los parámetros son opcionales y preseleccionan)
    @GetMapping("/nueva")
    public String nueva(@RequestParam(required = false) Long socioId,
                        @RequestParam(required = false) Long actividadId, Model model) {
        return mostrarFormulario(socioId, actividadId, model);
    }

    // POST /inscripciones -> aplica las reglas; si alguna falla, vuelve al formulario con el mensaje
    @PostMapping
    public String inscribir(@RequestParam(required = false) Long socioId,
                            @RequestParam(required = false) Long actividadId,
                            Model model, RedirectAttributes redirect) {
        if (socioId == null || actividadId == null) {
            model.addAttribute("error", "Elegí un socio y una actividad");
            return mostrarFormulario(socioId, actividadId, model);
        }
        try {
            Inscripcion inscripcion = inscripcionService.inscribir(socioId, actividadId);
            redirect.addFlashAttribute("exito", "Se inscribió a " + inscripcion.getSocio().getNombreCompleto()
                    + " en " + inscripcion.getActividad().getNombre());
            return "redirect:/actividades/" + actividadId + "/inscriptos";
        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getRegla() + ": " + e.getMessage());
            return mostrarFormulario(socioId, actividadId, model);
        } catch (RecursoNoEncontradoException e) {
            model.addAttribute("error", e.getMessage());
            return mostrarFormulario(socioId, actividadId, model);
        }
    }

    // POST /inscripciones/5/baja -> baja lógica y vuelta al listado de inscriptos de la actividad
    @PostMapping("/{id}/baja")
    public String darDeBaja(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            Inscripcion inscripcion = inscripcionService.darDeBaja(id);
            redirect.addFlashAttribute("exito", "Se dio de baja a " + inscripcion.getSocio().getNombreCompleto()
                    + " de " + inscripcion.getActividad().getNombre());
            return "redirect:/actividades/" + inscripcion.getActividad().getId() + "/inscriptos";
        } catch (RecursoNoEncontradoException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/actividades";
        }
    }

    private String mostrarFormulario(Long socioId, Long actividadId, Model model) {
        model.addAttribute("socios", socioService.listarTodos());
        model.addAttribute("actividades", actividadService.listar(null));
        model.addAttribute("socioId", socioId);
        model.addAttribute("actividadId", actividadId);
        return "inscripciones/formulario";
    }
}
