package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.repository.IProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProyectoService {

    @Autowired
    IProyectoRepository proyectoRepository;

    public Mono<Proyecto> findById(String id) {
        return proyectoRepository.findById(id);
    }

    public Flux<Proyecto> findAllByIdIn(List<String> proyectos) {
        return proyectoRepository.findAllByIdIn(proyectos);
    }

    public Mono<Proyecto> save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

}
