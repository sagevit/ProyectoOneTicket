package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.ProyectoService;
import es.jmdedios.proyectooneticket.service.UsuarioProyectoService;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
@RequestMapping("proyectos")
public class ProyectosController {

    @Autowired
    ProyectoService proyectoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioProyectoService usuarioProyectoService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return this.usuarioService.getUsuario();
    }

    @GetMapping("")
    public Mono<String> main(Authentication auth, final Model model) {
        return this.usuarioService
                .findByCodigo(auth.getName())
                .map(result -> {
                    if (result.getRol().equals(RolesEnum.ADMIN)) {
                        return "redirect:/admin";
                    } else {
                        model.addAttribute("rolManager", RolesEnum.MANAGER);
                        model.addAttribute("proyectos", proyectoService.buscarProyectosUsuario(result.getId()));
                        return "proyectos";
                    }
                });
    }

    @GetMapping("/nuevo")
    public String nuevo(final Model model) {
        model.addAttribute("proyecto", new Proyecto());
        return "formProyecto";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, final Model model) {
        model.addAttribute("proyecto", this.proyectoService.findById(id));
        return "formProyecto";
    }

    @PostMapping("/grabar")
    public String submit(@Valid Proyecto proyecto, Errors errores) {
        if (errores.hasErrors()) {
            return "formProyecto";
        }
        this.proyectoService.guardar(proyecto);
        return "redirect:/proyectos";
    }
}
