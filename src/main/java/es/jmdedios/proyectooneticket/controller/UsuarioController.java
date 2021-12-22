package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private IUsuarioRepository repository;
    private static final int DELAY_PER_ITEM_MS = 100;

    @Autowired
    public UsuarioController(IUsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list-usuarios")
    public String listUsuarios(final Model model) {

        Flux<Usuario> resultado = repository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
        model.addAttribute("usuarios", new ReactiveDataDriverContextVariable(resultado, 1));

        return "usuarios";
    }

    @PostMapping("/createUser")
    public String createUser(Usuario usuario) {
        repository.save(usuario);
        return "redirect:/usuario/list-usuarios";
    }

}
