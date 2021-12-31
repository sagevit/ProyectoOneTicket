package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.repository.IProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProyectoService {

    @Autowired
    IProyectoRepository proyectoRepository;

    @Autowired
    UsuarioProyectoService usuarioProyectoService;

    public Mono<Proyecto> findById(String id) {
        return proyectoRepository.findById(id);
    }

    public Flux<Proyecto> buscarProyectosUsuario(String usuario) {
        return this.usuarioProyectoService.findByUsuario(usuario)
                .concatMap(this::buildProyecto);
    }

    private Mono<Proyecto> buildProyecto(final UsuarioProyecto usuarioProyecto){
        return this.proyectoRepository.findById(usuarioProyecto.getProyecto());
    }

    public Mono<Proyecto> save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

}
