package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.TicketService;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    UsuarioService usuarioService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return this.usuarioService.getUsuario();
    }

    @GetMapping("")
    public Mono<String> main(Authentication auth, final Model model) {
        return this.usuarioService
                .findByCodigo(auth.getName())
                .map(result -> {
                    if (result.getRol().equals(RolesEnum.ADMIN)) {
                        return "redirect:/admin";
                    } else {
//                        model.addAttribute("rolManager", RolesEnum.MANAGER);
                        return "tickets";
                    }
                });
    }

    @GetMapping("/nuevo")
    public String nuevo(final Model model) {
        model.addAttribute("ticketDTO", new TicketDTO());
        return "formTicket";
    }

    @GetMapping("/grabar")
    public String submit(@Valid TicketDTO ticketDTO, Errors errores) {
        if (errores.hasErrors()) {
            return "formTicket";
        }
        //this.ticketService.guardar(ticketDTO);
        return "tickets";
    }

}
