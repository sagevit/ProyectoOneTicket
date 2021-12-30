package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.ProyectoService;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("proyectos")
public class ProyectosController {

    private static final int DELAY_PER_ITEM_MS = 100;

    @Autowired
    ProyectoService proyectoService;

    @Autowired
    UsuarioService usuarioService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return usuarioService.getUsuario();
    }

    @GetMapping("")
    public String main(final Model model) {

        model.addAttribute("rolManager", RolesEnum.MANAGER);

        //userLogged().subscribe(result -> System.out.println(result));

        //Flux<Proyecto> resultado1 = repository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
        //Flux<Proyecto> resultado1 = repository.findByNombreEquals("Proyecto 2");
        Flux<Proyecto> resultado1 = proyectoService.findAllByIdIn(Arrays.asList("61ccec4b51819e02f99d24fc", "61ccebc8ad00395b381c198c"));
        model.addAttribute("proyectos", resultado1);

        return "proyectos";
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
        proyectoService.save(proyecto).subscribe();
        return "redirect:/admin";
    }
}
