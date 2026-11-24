package ar.edu.siglo21.socioclub.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import ar.edu.siglo21.socioclub.model.EstadoCuota;
import ar.edu.siglo21.socioclub.model.Socio;
import ar.edu.siglo21.socioclub.service.InscripcionService;
import ar.edu.siglo21.socioclub.service.RecursoNoEncontradoException;
import ar.edu.siglo21.socioclub.service.ReglaNegocioException;
import ar.edu.siglo21.socioclub.service.SocioService;

/**
 * Socios en el sistema de secretaría: listado paginado con búsqueda por apellido, alta y edición.
 */
@Controller
@RequestMapping("/socios")
public class SocioController {

    private final SocioService socioService;
    private final InscripcionService inscripcionService;

    public SocioController(SocioService socioService, InscripcionService inscripcionService) {
        this.socioService = socioService;
        this.inscripcionService = inscripcionService;
    }

    // GET /socios?apellido=gom&page=0 -> Spring arma el Pageable con page, size y sort de la URL
    @GetMapping
    public String listar(@RequestParam(required = false) String apellido,
                         @PageableDefault(size = 8, sort = {"apellido", "nombre"}, direction = Sort.Direction.ASC) Pageable pageable,
                         Model model) {
        Page<Socio> pagina = socioService.listar(apellido, pageable);
        model.addAttribute("pagina", pagina);
        model.addAttribute("apellido", apellido == null ? "" : apellido);
        return "socios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        return mostrarFormulario(new Socio(), model);
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            Socio socio = socioService.buscarPorId(id);
            model.addAttribute("inscripciones", inscripcionService.listarActivasDeSocio(id));
            return mostrarFormulario(socio, model);
        } catch (RecursoNoEncontradoException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/socios";
        }
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("socio") Socio socio, BindingResult binding,
                          Model model, RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            model.addAttribute("error", "Revisá la fecha de nacimiento");
            return mostrarFormulario(socio, model);
        }
        boolean esAlta = socio.getId() == null;
        try {
            Socio guardado = socioService.guardar(socio);
            redirect.addFlashAttribute("exito", (esAlta ? "Se dio de alta a " : "Se actualizó a ") + guardado.getNombreCompleto());
            return "redirect:/socios";
        } catch (ReglaNegocioException | RecursoNoEncontradoException e) {
            model.addAttribute("error", e.getMessage());
            return mostrarFormulario(socio, model);
        }
    }

    private String mostrarFormulario(Socio socio, Model model) {
        model.addAttribute("socio", socio);
        model.addAttribute("estados", EstadoCuota.values());
        return "socios/formulario";
    }
}
