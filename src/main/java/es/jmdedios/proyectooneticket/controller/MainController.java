package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Controller
public class MainController {

    @Autowired
    IUsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String index(final Model model) throws ExecutionException, InterruptedException {

        model.addAttribute("usuario", this.getUsuario().switchIfEmpty(
                Mono.just(new Usuario("1", "pepe", "pepe", null))));

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
