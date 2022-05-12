package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    public Mono<Usuario> findById(String id) {
        if (Objects.isNull(id)) {
            return Mono.just(new Usuario(null, null, "---", null, null));
        } else {
            return this.usuarioRepository.findById(id);
        }
    }

    public Mono<Usuario> findByCodigo(String username) {
        return this.usuarioRepository.findByCodigo(username);
    }

    public Flux<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

    public Flux<Usuario> findByNombreContainsIgnoreCase(String nombre) {
        return this.usuarioRepository.findByNombreContainsIgnoreCase(nombre);
    }

    public Flux<Usuario> findByRol(String rol) {
        return this.usuarioRepository.findByRol(rol);
    }

    public Flux<Usuario> findByNombreContainsIgnoreCaseAndRol(String nombre, String rol) {
        return this.usuarioRepository.findByNombreContainsIgnoreCaseAndRol(nombre, rol);
    }

    public Flux<Usuario> findByRolNot(String rol) { return this.usuarioRepository.findByRolNot(rol); }

    public Flux<Usuario> findByNombreContainsIgnoreCaseAndRolNot(String filtro, String rol) {
        return this.usuarioRepository.findByNombreContainsIgnoreCaseAndRolNot(filtro, rol);
    }

    public Mono<Usuario> save(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    public Mono<Usuario> getUsuario () {
        return this.getUsername()
                .flatMap(username -> this.findByCodigo(username))
                .switchIfEmpty(Mono.just(new Usuario()));
    }

    private Mono<String> getUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getName());
    }
}
