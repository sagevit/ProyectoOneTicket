package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    public Mono<Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    public Mono<Usuario> findByCodigo(String username) {
        return usuarioRepository.findByCodigo(username);
    }

    public Flux<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Flux<Usuario> findByNombreContainsIgnoreCase(String nombre) {
        return usuarioRepository.findByNombreContainsIgnoreCase(nombre);
    }

    public Flux<Usuario> findByRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    public Flux<Usuario> findByNombreContainsIgnoreCaseAndRol(String nombre, String rol) {
        return usuarioRepository.findByNombreContainsIgnoreCaseAndRol(nombre, rol);
    }

    public Mono<Usuario> save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Mono<Usuario> getUsuario () {
        return this.getUsername()
                .flatMap(username -> {
                    return this.findByCodigo(username);
                })
                .switchIfEmpty(Mono.just(new Usuario()));
    }

    private Mono<String> getUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getName());
    }
}
