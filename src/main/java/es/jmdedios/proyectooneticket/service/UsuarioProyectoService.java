package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.repository.IUsuarioProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioProyectoService {

    @Autowired
    IUsuarioProyectoRepository usuarioProyectoRepository ;

    public Flux<UsuarioProyecto> findByUsuario (String usuario) {
        return usuarioProyectoRepository.findByUsuario(usuario);
    }

    public Mono<UsuarioProyecto> save(UsuarioProyecto usuarioProyecto) {
        return usuarioProyectoRepository.save(usuarioProyecto);
    }

}
