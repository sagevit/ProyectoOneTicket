package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.repository.IUsuarioProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UsuarioProyectoService {

    @Autowired
    IUsuarioProyectoRepository usuarioProyectoRepository ;

    public Flux<UsuarioProyecto> findByUsuario (String usuario) {
        return this.usuarioProyectoRepository.findByUsuario(usuario);
    }

    public Flux<UsuarioProyecto> findByProyecto (String proyecto) {
        return this.usuarioProyectoRepository.findByProyecto(proyecto);
    }

    public Mono<UsuarioProyecto> save(UsuarioProyecto usuarioProyecto) {
        return this.usuarioProyectoRepository.save(usuarioProyecto);
    }

    public Mono<Void> deleteByUsuarioAndProyecto(String usuario, String proyecto) {
        return this.usuarioProyectoRepository.deleteByUsuarioAndProyecto(usuario, proyecto);
    }

}
