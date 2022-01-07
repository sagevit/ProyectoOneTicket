package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.model.Ticket;
import es.jmdedios.proyectooneticket.repository.ITicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TicketService {

    @Autowired
    ITicketRepository ticketRepository;

    public Mono<Ticket> guardar(TicketDTO ticketDTO) {
        return this.ticketRepository.save(new Ticket(ticketDTO));
    }

    public Mono<Ticket> save(Ticket ticket) { return this.ticketRepository.save(ticket); }

}
