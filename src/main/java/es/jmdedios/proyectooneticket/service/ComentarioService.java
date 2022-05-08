package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.model.Comentario;
import es.jmdedios.proyectooneticket.model.Ticket;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.repository.IComentarioRepository;
import es.jmdedios.proyectooneticket.repository.ITicketRepository;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class ComentarioService {

    @Autowired
    IComentarioRepository comentarioRepository;

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    UsuarioProyectoService usuarioProyectoService;

    public Flux<Comentario> findAllByTicketIdOrderBySecuencia (String ticketId) {
        return this.comentarioRepository.findAllByTicketIdOrderBySecuencia(ticketId);
    }

    public Mono<Comentario> guardar (Comentario comentario) {
        return this.comentarioRepository.save(comentario);
    }

}
