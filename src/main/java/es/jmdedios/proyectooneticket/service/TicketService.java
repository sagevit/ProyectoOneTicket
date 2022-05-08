package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.model.Ticket;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.repository.ITicketRepository;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class TicketService {

    @Autowired
    ITicketRepository ticketRepository;

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    UsuarioProyectoService usuarioProyectoService;

    public Mono<Ticket> findById(String id) {
        return this.ticketRepository.findById(id);
    }

    public Flux<Ticket> findAllByProyectoIdAndAsignadaOrderBySecuencia (String proyectoId, boolean asignada) {
        return this.ticketRepository.findAllByProyectoIdAndAsignadaOrderBySecuencia(proyectoId, asignada);
    }

    public Flux<Ticket> findAllByProyectoIdAndAsignadoIdAndAsignadaOrderBySecuenciaDesc (String proyectoId, String usuarioId, boolean asignada) {
        return this.ticketRepository.findAllByProyectoIdAndAsignadoIdAndAsignadaOrderBySecuenciaDesc(proyectoId, usuarioId, asignada);
    }

    public Flux<Ticket> findAllByProyectoIdAndPropietarioIdOrderBySecuenciaDesc (String proyectoId, String usuarioId) {
        return this.ticketRepository.findAllByProyectoIdAndPropietarioIdOrderBySecuenciaDesc(proyectoId, usuarioId);
    }
    public Flux<Usuario> buscarManagersOrDevelopersProyecto(String proyecto) {
        return this.usuarioProyectoService.findByProyecto(proyecto)
                .concatMap(this::buildUsuario);
    }

    private Mono<Usuario> buildUsuario(final UsuarioProyecto usuarioProyecto){
        return this.usuarioRepository.findById(usuarioProyecto.getUsuario())
                .filter(f -> Arrays.asList(RolesEnum.MANAGER, RolesEnum.DEVELOPER).contains(f.getRol()));
    }

    public Mono<Ticket> guardar (TicketDTO ticketDTO) {
        return this.ticketRepository.save(new Ticket(ticketDTO));
    }

    public Mono<Ticket> save (Ticket ticket) { return this.ticketRepository.save(ticket); }

}
