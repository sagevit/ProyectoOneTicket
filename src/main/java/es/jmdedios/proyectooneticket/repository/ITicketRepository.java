package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Ticket;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@EnableReactiveMongoRepositories
public interface ITicketRepository extends ReactiveMongoRepository<Ticket, String> {

    Flux<Ticket> findAllByProyectoIdAndAsignadaOrderBySecuencia(String proyectoId, boolean asignada);

    Flux<Ticket> findAllByProyectoIdAndAsignadoIdAndAsignadaOrderBySecuenciaDesc(String proyectoId, String usuarioId, boolean asignada);

}
