package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.model.Notificacion;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.*;
import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import es.jmdedios.proyectooneticket.utilities.NotificacionEnum;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;

@Controller
@RequestMapping("tickets")
public class TicketsController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProyectoService proyectoService;

    @Autowired
    UsuarioProyectoService usuarioProyectoService;

    @Autowired
    NotificacionService notificacionService;

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
    public String nuevo(@PathVariable String proyectoId, Authentication auth, final Model model) {

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setProyectoId(proyectoId);
        ticketDTO.setRealizado(0);
        ticketDTO.setEstado(EstadosEnum.INICIAL);
        ticketDTO.setPropietarioId(this.usuarioService.findByCodigo(auth.getName())
                .map(Usuario::getId)
                .block());

        model.addAttribute("rolManager", RolesEnum.MANAGER);
        model.addAttribute("rolDeveloper", RolesEnum.DEVELOPER);
        model.addAttribute("ticketDTO", ticketDTO);
        //TODO: solo buscar los usuarios cuando el usuario sea Manager
        model.addAttribute("usuariosAsignables", this.ticketService.buscarManagersOrDevelopersProyecto(proyectoId));

        return "formTicket";
    }

    @GetMapping("{proyectoId}/editar/{ticketId}")
    public String editar(@PathVariable String proyectoId, @PathVariable String ticketId, final Model model) {

        model.addAttribute("rolManager", RolesEnum.MANAGER);
        model.addAttribute("rolDeveloper", RolesEnum.DEVELOPER);
        model.addAttribute("ticketDTO", new TicketDTO(this.ticketService.findById(ticketId)));
        //TODO: solo buscar los usuarios cuando el usuario sea Manager
        model.addAttribute("usuariosAsignables", this.ticketService.buscarManagersOrDevelopersProyecto(proyectoId));

        return "formTicket";
    }

    @PostMapping("{proyectoId}/grabar")
    public String submit(@Valid TicketDTO ticketDTO, Errors errores, Authentication auth) {
        if (errores.hasErrors()) {
            return "formTicket";
        }
        this.ticketService.guardar(ticketDTO)
                .subscribe(t -> this.usuarioService.findByCodigo(auth.getName())
                    .subscribe(u -> {
                        if (u.getRol().equals(RolesEnum.USER)) {
                            // Si la persona que genera el registro es un rol usuario hay que indicar a los manager del proyecto que hay un nuevo ticket
                            this.usuarioProyectoService.findByProyecto(t.getProyectoId())
                                    .publishOn(Schedulers.boundedElastic())
                                    .filter(up -> Boolean.TRUE.equals(this.usuarioService.findById(up.getUsuario())
                                            .map(user -> user.getRol().equals(RolesEnum.MANAGER)
                                            ).block()))
                                    .subscribe(up -> this.notificacionService.guardar(new Notificacion(null, t.getId(),
                                            NotificacionEnum.NUEVO.getDescripcion().concat(" #").concat(Long.toString(t.getSecuencia())),
                                            "/tickets/".concat(t.getProyectoId()), up.getUsuario(), Boolean.TRUE, null)).subscribe());
                        } else {
                            // Si no lo es hay que comprobar si el ticket ya está asignado. Si es así se crea un
                            // notificación a esa persona si no es igual que el propietario
                            if (t.isAsignada() && !t.getAsignadoId().equals(t.getPropietarioId())) {
                                this.notificacionService.guardar(new Notificacion( null, t.getId(),
                                        NotificacionEnum.ASIGNACION.getDescripcion().concat(" #").concat(Long.toString(t.getSecuencia())),
                                        "/comentarios/".concat(t.getId()), t.getAsignadoId(), Boolean.TRUE, null)).subscribe();
                            }
                        }
                    }));
        return "redirect:/tickets/"+ticketDTO.getProyectoId();
    }

}
