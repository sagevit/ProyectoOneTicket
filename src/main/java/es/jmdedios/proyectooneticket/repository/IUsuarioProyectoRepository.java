package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@EnableReactiveMongoRepositories
public interface IUsuarioProyectoRepository extends ReactiveMongoRepository<UsuarioProyecto, String> {

    /*
    Flux<UsuarioProyecto> findById_usuario(String usuario);
     */

}
