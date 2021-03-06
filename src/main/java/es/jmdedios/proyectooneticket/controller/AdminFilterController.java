package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("admin/filter")
public class AdminFilterController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("")
    public String noFiltrado(Model model) {
        model.addAttribute("usuarios", this.usuarioService.findAll());
        return "admin :: #listUsuarios";
    }

    @GetMapping("/{filtro}")
    public String filtrado(@PathVariable String filtro, Model model) {
        Flux<Usuario> resultado;
        String valores[] = filtro.split("&");
        if (!valores[0].equals("-") && !valores[1].equals("-")) {
            resultado = this.usuarioService.findByNombreContainsIgnoreCaseAndRol(valores[0], valores[1]);
        } else if (!valores[0].equals("-") || valores[1].equals("-")) {
            resultado = this.usuarioService.findByNombreContainsIgnoreCase(valores[0]);
        } else {
            resultado = this.usuarioService.findByRol(valores[1]);
        }
        model.addAttribute("usuarios", resultado);
        return "admin :: #listUsuarios";
    }
}
