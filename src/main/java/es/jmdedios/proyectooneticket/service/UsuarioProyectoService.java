package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.repository.IUsuarioProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UsuarioProyectoService {

    @Autowired
    IUsuarioProyectoRepository usuarioProyectoRepository ;

    /*
    public List<String> findAllById_usuario (String usuario) {
        return usuarioProyectoRepository
                .findAllById_usuario(usuario)
                .map(r -> r.getId_proyecto())
                .toStream().collect(Collectors.toList());
    }
     */

    /*
    public Flux<String> findAllById_usuario (String usuario) {
        System.out.println(usuario);
        return usuarioProyectoRepository
                .findAllById_usuario(usuario)
                .map(r -> r.getId_proyecto());
    }
     */

    public Mono<UsuarioProyecto> save(UsuarioProyecto usuarioProyecto) {
        return usuarioProyectoRepository.save(usuarioProyecto);
    }

}
