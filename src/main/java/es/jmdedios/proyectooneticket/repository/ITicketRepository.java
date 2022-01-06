package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Ticket;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableReactiveMongoRepositories
public interface ITicketRepository extends ReactiveMongoRepository<Ticket, String> {

}
