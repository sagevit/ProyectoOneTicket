package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import reactor.core.publisher.Mono;

@Controller
public class MainController {

    @Autowired
    UsuarioService usuarioService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return usuarioService.getUsuario();
    }

    @GetMapping("/")
    public String index(Authentication auth) {
        if (auth.getName().equals("admin")) {
            return "redirect:/admin";
        }
        return "index";
    }

}
