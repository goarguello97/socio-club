package ar.edu.siglo21.socioclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    // La pantalla principal del sistema de secretaría es el listado de actividades
    @GetMapping("/")
    public String inicio() {
        return "redirect:/actividades";
    }
}
