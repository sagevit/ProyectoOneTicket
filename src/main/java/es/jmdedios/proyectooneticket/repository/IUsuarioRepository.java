package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IUsuarioRepository extends ReactiveMongoRepository<Usuario, String> {

    Mono<Usuario> findByCodigo(String username);

}
