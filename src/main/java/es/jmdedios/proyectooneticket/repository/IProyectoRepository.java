package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@EnableReactiveMongoRepositories
public interface IProyectoRepository extends ReactiveMongoRepository<Proyecto, String> {

    Mono<Proyecto> findById (String id);

    Flux<Proyecto> findAllByIdIn(List<String> proyectos);

}
