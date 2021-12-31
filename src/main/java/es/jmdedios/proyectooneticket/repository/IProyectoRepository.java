package es.jmdedios.proyectooneticket.repository;

import es.jmdedios.proyectooneticket.model.Proyecto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableReactiveMongoRepositories
public interface IProyectoRepository extends ReactiveMongoRepository<Proyecto, String> {

}
