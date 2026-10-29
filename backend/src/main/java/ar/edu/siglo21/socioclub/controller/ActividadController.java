package ar.edu.siglo21.socioclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.siglo21.socioclub.model.Actividad;
import ar.edu.siglo21.socioclub.service.ActividadService;
import ar.edu.siglo21.socioclub.service.RecursoNoEncontradoException;
import ar.edu.siglo21.socioclub.service.ReglaNegocioException;

/**
 * ABM de actividades del sistema de secretaría (vistas Thymeleaf).
 * El controlador solo recibe la petición, llama al servicio y elige qué vista mostrar.
 */
@Controller
@RequestMapping("/actividades")
public class ActividadController {

    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    // GET /actividades?disciplina=Fútbol
    @GetMapping
    public String listar(@RequestParam(required = false) String disciplina, Model model) {
        model.addAttribute("actividades", actividadService.listar(disciplina));
        model.addAttribute("disciplinas", actividadService.listarDisciplinas());
        model.addAttribute("disciplinaSeleccionada", disciplina);
        return "actividades/lista";
    }

    // GET /actividades/nueva -> formulario vacío
    @GetMapping("/nueva")
    public String nueva(Model model) {
        return mostrarFormulario(new Actividad(), model);
    }

    // GET /actividades/5/editar -> formulario con los datos cargados
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            return mostrarFormulario(actividadService.buscarPorId(id), model);
        } catch (RecursoNoEncontradoException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/actividades";
        }
    }

    // POST /actividades/guardar -> alta o modificación según venga o no el id
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("actividad") Actividad actividad, BindingResult binding,
                          Model model, RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            model.addAttribute("error", "Revisá los campos numéricos: edades, cupo y cuota deben ser números");
            return mostrarFormulario(actividad, model);
        }
        boolean esAlta = actividad.getId() == null;
        try {
            Actividad guardada = actividadService.guardar(actividad);
            redirect.addFlashAttribute("exito", esAlta
                    ? "Se creó la actividad \"" + guardada.getNombre() + "\""
                    : "Se actualizó la actividad \"" + guardada.getNombre() + "\"");
            return "redirect:/actividades";
        } catch (ReglaNegocioException | RecursoNoEncontradoException e) {
            // Volvemos al formulario conservando lo que escribió el usuario
            model.addAttribute("error", e.getMessage());
            return mostrarFormulario(actividad, model);
        }
    }

    // POST /actividades/5/eliminar
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            actividadService.eliminar(id);
            redirect.addFlashAttribute("exito", "Se eliminó la actividad");
        } catch (ReglaNegocioException | RecursoNoEncontradoException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/actividades";
    }

    private String mostrarFormulario(Actividad actividad, Model model) {
        model.addAttribute("actividad", actividad);
        model.addAttribute("disciplinas", actividadService.listarDisciplinas());
        return "actividades/formulario";
    }
}
