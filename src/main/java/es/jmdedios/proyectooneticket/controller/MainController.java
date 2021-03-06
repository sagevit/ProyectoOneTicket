package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class MainController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/")
    public Mono<String> index(Authentication auth) {
        return this.usuarioService
            .findByCodigo(auth.getName())
            .map(result -> {
                if (result.getRol().equals(RolesEnum.ADMIN)) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/proyectos";
                }
            })
            .switchIfEmpty(Mono.just("userNotFound"));
    }
}
