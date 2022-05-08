package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Comentario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@EnableReactiveMongoRepositories
public interface IComentarioRepository extends ReactiveMongoRepository<Comentario, String> {

    Flux<Comentario> findAllByTicketIdOrderBySecuencia(String ticketId);

}
