package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IProyectoRepository;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

    private static final int DELAY_PER_ITEM_MS = 100;

    @Autowired
    private IProyectoRepository repository;

    @Autowired
    UsuarioService usuarioService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return usuarioService.getUsuario();
    }

    @Autowired
    public ProyectoController(IProyectoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list-proyectos")
    public String listProyectos(final Model model) throws InterruptedException {

        //userLogged().subscribe(result -> System.out.println(result));

        //Flux<Proyecto> resultado1 = repository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
        //Flux<Proyecto> resultado1 = repository.findByNombreEquals("Proyecto 2");
        Flux<Proyecto> resultado1 = repository.findByIdIn(Arrays.asList("61b4b265d03f9eed7ba3e515", "61b4b265d03f9eed7ba3e517"));
        model.addAttribute("proyectos", resultado1);

        return "proyectos";
    }

}
