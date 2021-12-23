package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Proyecto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
@EnableReactiveMongoRepositories
public interface IProyectoRepository extends ReactiveMongoRepository<Proyecto, String> {

    //private final ReactiveMongoTemplate template;

    //@Query(value = "{'proyecto.nombre' : ?0}")
    //Mono<Proyecto> findProyectosByNombre(String nombre);

    Flux<Proyecto> findByNombreEquals(final String nombre);

    Flux<Proyecto> findByIdIn(final List<String> ids);

}
