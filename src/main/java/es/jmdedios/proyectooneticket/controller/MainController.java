package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import reactor.core.publisher.Mono;

@Controller
public class MainController {

    @Autowired
    IUsuarioRepository usuarioRepository;

    @ModelAttribute("usuario")
    public Mono<Usuario> messages() {
        return this.getUsuario()
                .switchIfEmpty(Mono.just(new Usuario("0", "No localizado", "No localizado", null)));
    }

    @GetMapping("/")
    public String index(Authentication auth) {

        if (auth.getName().equals("admin")) {
            return "redirect:/admin";
        }
        return "index";
    }

    private Mono<Usuario> getUsuario () {
        return this.getUsername()
                .flatMap(username -> {
                    return usuarioRepository.findByCodigo(username);
                });
    }

    private Mono<String> getUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getName());
    }

}
