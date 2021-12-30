package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface IUsuarioRepository extends ReactiveMongoRepository<Usuario, String> {

    Mono<Usuario> findById (String id);

    Mono<Usuario> findByCodigo (String username);

    Flux<Usuario> findAll();

    Flux<Usuario> findByNombreContainsIgnoreCase(String nombre);

    Flux<Usuario> findByRol(String rol);

    Flux<Usuario> findByNombreContainsIgnoreCaseAndRol(String nombre, String rol);

}
