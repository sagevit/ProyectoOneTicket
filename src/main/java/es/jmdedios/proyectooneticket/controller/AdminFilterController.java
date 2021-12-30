package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/filter")
public class AdminFilterController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("")
    public String noFiltrado(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "admin :: #listUsuarios";
    }

    @GetMapping("/{filtro}")
    public String filtrado(@PathVariable String filtro, Model model) {
        Flux<Usuario> resultado = null;
        String valores[] = filtro.split("&");
        if (!valores[0].equals("-") && !valores[1].equals("-")) {
            resultado = usuarioService.findByNombreContainsIgnoreCaseAndRol(valores[0], valores[1]);
        } else if (!valores[0].equals("-") || valores[1].equals("-")) {
            resultado = usuarioService.findByNombreContainsIgnoreCase(valores[0]);
        } else {
            resultado = usuarioService.findByRol(valores[1]);
        }
        model.addAttribute("usuarios", resultado);
        return "admin :: #listUsuarios";
    }
}
