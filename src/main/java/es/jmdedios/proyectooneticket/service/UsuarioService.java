package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    public Mono<Usuario> findByCodigo(String username) {
        return usuarioRepository.findByCodigo(username);
    }

    public Flux<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

}
