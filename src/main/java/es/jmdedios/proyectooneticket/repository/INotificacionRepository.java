package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Notificacion;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Repository
@EnableReactiveMongoRepositories
public interface INotificacionRepository extends ReactiveMongoRepository<Notificacion, String> {

    @Tailable
    Flux<Notificacion> findWithTailableCursorByUsuarioIdAndVisible(String usuarioId, Boolean visible);

}
