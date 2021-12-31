package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.service.ProyectoService;
import es.jmdedios.proyectooneticket.service.UsuarioProyectoService;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
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
        return usuarioService.getUsuario();
    }

    @GetMapping("")
    public Mono<String> main(Authentication auth, final Model model) {
        return usuarioService
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
        model.addAttribute("proyecto", proyectoService.findById(id));
        return "formProyecto";
    }

    @PostMapping("/grabar")
    public String submit(@Valid Proyecto proyecto, Errors errores) {
        if (errores.hasErrors()) {
            return "formProyecto";
        }
        /* Si el id del proyecto es nulo o es blanco significa que es un alta de proyecto. Entonces
           se graba el proyecto y el registro de usuario-proyecto para asignárselo al usuario (manager)
           que lo da de ALTA */
        if (proyecto.getId() == null || proyecto.getId().isBlank()) {
            proyectoService
                    .save(proyecto)
                    .handle((document, sink) -> {
                        usuarioProyectoService
                                .save(new UsuarioProyecto(null, document.getId_manager(), document.getId()))
                                .subscribe();
                    })
                    .subscribe();
        /* En otro caso es que es una MODIFICACIÓN y se únicamente modifica el proyecto */
        } else {
            proyectoService
                    .save(proyecto)
                    .subscribe();
        }
        return "redirect:/proyectos";
    }
}
