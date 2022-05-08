package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.ProyectoService;
import es.jmdedios.proyectooneticket.service.TicketService;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import es.jmdedios.proyectooneticket.utilities.PrioridadEnum;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import es.jmdedios.proyectooneticket.utilities.TiposEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
@RequestMapping("tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProyectoService proyectoService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return this.usuarioService.getUsuario();
    }

    @GetMapping("{proyectoId}")
    public Mono<String> main(@PathVariable String proyectoId, Authentication auth, final Model model) {
        return this.usuarioService
                .findByCodigo(auth.getName())
                .map(result -> {
                    if (result.getRol().equals(RolesEnum.ADMIN)) {
                        return "redirect:/admin";
                    } else {
                        model.addAttribute("rolManager", RolesEnum.MANAGER);
                        model.addAttribute("proyecto", this.proyectoService.findById(proyectoId));
                        if (result.getRol().equals(RolesEnum.USER)) {
                            model.addAttribute("ticketsAsignados", this.ticketService
                                    .findAllByProyectoIdAndPropietarioIdOrderBySecuenciaDesc(proyectoId, result.getId()));
                        } else {
                            model.addAttribute("ticketsAsignados", this.ticketService
                                    .findAllByProyectoIdAndAsignadoIdAndAsignadaOrderBySecuenciaDesc(proyectoId, result.getId(), true));
                        }
                        //TODO: solo buscar los tickets no asignados cuando el usuario sea Manager
                        model.addAttribute("ticketsNoAsignados", this.ticketService
                                .findAllByProyectoIdAndAsignadaOrderBySecuencia(proyectoId, false));
                        return "tickets";
                    }
                });
    }

    @GetMapping("{proyectoId}/nuevo")
    public String nuevo(@PathVariable String proyectoId, final Model model) {

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setProyectoId(proyectoId);
        ticketDTO.setRealizado(0);
        ticketDTO.setEstado(EstadosEnum.INICIAL);

        model.addAttribute("rolManager", RolesEnum.MANAGER);
        model.addAttribute("rolDeveloper", RolesEnum.DEVELOPER);
        model.addAttribute("ticketDTO", ticketDTO);
        model.addAttribute("tipos", TiposEnum.values());
        model.addAttribute("prioridades", PrioridadEnum.values());
        //TODO: solo buscar los usuarios cuando el usuario sea Manager
        model.addAttribute("usuariosAsignables", this.ticketService.buscarManagersOrDevelopersProyecto(proyectoId));

        return "formTicket";
    }

    @GetMapping("{proyectoId}/editar/{ticketId}")
    public String editar(@PathVariable String proyectoId, @PathVariable String ticketId, final Model model) {

        TicketDTO ticketDTO = new TicketDTO(this.ticketService.findById(ticketId));

        model.addAttribute("rolManager", RolesEnum.MANAGER);
        model.addAttribute("rolDeveloper", RolesEnum.DEVELOPER);
        model.addAttribute("ticketDTO", ticketDTO);
        model.addAttribute("tipos", TiposEnum.values());
        model.addAttribute("prioridades", PrioridadEnum.values());
        //TODO: solo buscar los usuarios cuando el usuario sea Manager
        model.addAttribute("usuariosAsignables", this.ticketService.buscarManagersOrDevelopersProyecto(proyectoId));

        return "formTicket";
    }

    @PostMapping("{proyectoId}/grabar")
    public String submit(@Valid TicketDTO ticketDTO, Errors errores) {
        if (errores.hasErrors()) {
            return "formTicket";
        }
        this.ticketService.guardar(ticketDTO).subscribe();
        return "redirect:/tickets/"+ticketDTO.getProyectoId();
    }

}
