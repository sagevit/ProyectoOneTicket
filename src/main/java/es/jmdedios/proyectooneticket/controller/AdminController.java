package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import reactor.core.publisher.Mono;

@Controller
public class AdminController {

    @Autowired
    UsuarioService usuarioService;

    @ModelAttribute("logged")
    public Mono<Usuario> messages() {
        return this.getUsuario()
                .switchIfEmpty(Mono.just(new Usuario(null, null, "No localizado", "No localizado")));
    }

    @GetMapping("/admin")
    public String index(Authentication auth, final Model model) {
        if (!auth.getName().equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("usuarios", usuarioService.findAll());

        return "admin";
    }

    private Mono<Usuario> getUsuario () {
        return this.getUsername()
                .flatMap(username -> {
                    return usuarioService.findByCodigo(username);
                });
    }

    private Mono<String> getUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getName());
    }

}
