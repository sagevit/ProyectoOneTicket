package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface IUsuarioProyectoRepository extends ReactiveMongoRepository<UsuarioProyecto, String> {

    Flux<UsuarioProyecto> findByUsuario (String usuario);

    Flux<UsuarioProyecto> findByProyecto (String proyecto);

    Mono<Void> deleteByUsuarioAndProyecto (String usuario, String proyecto);

}
