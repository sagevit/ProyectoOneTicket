package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.repository.IProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Controller
@RequestMapping("/proyecto")
public class ProyectoController {

    private IProyectoRepository repository;
    private static final int DELAY_PER_ITEM_MS = 100;

    @Autowired
    public ProyectoController(IProyectoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list-proyectos")
    public String listProyectos(final Model model) {

        //Flux<Proyecto> resultado1 = repository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
        //Flux<Proyecto> resultado1 = repository.findByNombreEquals("Proyecto 2");
        Flux<Proyecto> resultado1 = repository.findByIdIn(Arrays.asList("61b4b265d03f9eed7ba3e515", "61b4b265d03f9eed7ba3e517"));
        model.addAttribute("proyectos", new ReactiveDataDriverContextVariable(resultado1, 1));

        return "proyectos";
    }

}
