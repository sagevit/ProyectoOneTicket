package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Comentario;
import es.jmdedios.proyectooneticket.model.Notificacion;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.ComentarioService;
import es.jmdedios.proyectooneticket.service.NotificacionService;
import es.jmdedios.proyectooneticket.service.TicketService;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.NotificacionEnum;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("comentarios")
public class ComentariosController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    NotificacionService notificacionService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return this.usuarioService.getUsuario();
    }

    @GetMapping("{ticketId}")
    public Mono<String> main(@PathVariable String ticketId, Authentication auth, final Model model) {
        return this.usuarioService
                .findByCodigo(auth.getName())
                .map(result -> {
                    if (result.getRol().equals(RolesEnum.ADMIN)) {
                        return "redirect:/admin";
                    } else {
                        model.addAttribute("ticket", this.ticketService.findById(ticketId));
                        model.addAttribute("asignado", this.ticketService.findById(ticketId)
                                .flatMap(t -> this.usuarioService.findById(t.getAsignadoId())));
                        model.addAttribute("propietario", this.ticketService.findById(ticketId)
                                .flatMap(t -> this.usuarioService.findById(t.getPropietarioId())));
                        model.addAttribute("comentarios", this.comentarioService.findAllByTicketIdOrderBySecuencia(ticketId));
                        return "comentarios";
                    }
                });
    }

    @GetMapping("{ticketId}/nuevo")
    public String nuevo(@PathVariable String ticketId, final Model model) {

        model.addAttribute("ticket", this.ticketService.findById(ticketId));
        model.addAttribute("asignado", this.ticketService.findById(ticketId)
                .flatMap(t -> this.usuarioService.findById(t.getAsignadoId())));
        model.addAttribute("propietario", this.ticketService.findById(ticketId)
                .flatMap(t -> this.usuarioService.findById(t.getPropietarioId())));
        model.addAttribute("comentarios", this.comentarioService.findAllByTicketIdOrderBySecuencia(ticketId));
        model.addAttribute("comentario", new Comentario(this.ticketService.findById(ticketId)));
        return "formComentario";
    }

    @PostMapping("{ticketId}/grabar")
    public String submit(@Valid Comentario comentario, Errors errores, Authentication auth) {
        if (errores.hasErrors()) {
            return "formComentario";
        }
        comentario.setFechaCreacion(LocalDate.now());
        this.comentarioService.guardar(comentario).subscribe();
        this.ticketService.actualizarTicketComentario(comentario)
            .subscribe(ticket -> {
                this.usuarioService.findByCodigo(auth.getName())
                    .subscribe(u -> {
                        String usuarioId = null;
                        if (u.getId().equals(ticket.getPropietarioId())) {
                            usuarioId = ticket.getAsignadoId();
                        } else {
                            usuarioId = ticket.getPropietarioId();
                        }
                        this.notificacionService.guardar(new Notificacion(null, ticket.getId(),
                                NotificacionEnum.ACTUALIZACION.getDescripcion().concat(" #").concat(Long.toString(ticket.getSecuencia())),
                                "/comentarios/".concat(ticket.getId()).concat("#comentario").concat(Long.toString(comentario.getSecuencia())), usuarioId, Boolean.TRUE, null)).subscribe();
                    });
            });

        return "redirect:/comentarios/"+comentario.getTicketId();
    }

}
