package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Proyecto;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IProyectoRepository extends ReactiveMongoRepository<Proyecto, String> {

    //private final ReactiveMongoTemplate template;

    //@Query(value = "{'proyecto.nombre' : ?0}")
    //Mono<Proyecto> findProyectosByNombre(String nombre);

    Flux<Proyecto> findByNombreEquals(final String nombre);

    Flux<Proyecto> findByIdIn(final List<String> ids);

}
