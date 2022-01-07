package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.repository.IProyectoRepository;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProyectoService {

    @Autowired
    IProyectoRepository proyectoRepository;

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    UsuarioProyectoService usuarioProyectoService;

    public Mono<Proyecto> findById(String id) {
        return this.proyectoRepository.findById(id);
    }

    public Flux<Proyecto> buscarProyectosUsuario(String usuario) {
        return this.usuarioProyectoService.findByUsuario(usuario)
                .concatMap(this::buildProyecto);
    }

    private Mono<Proyecto> buildProyecto(final UsuarioProyecto usuarioProyecto){
        return this.proyectoRepository.findById(usuarioProyecto.getProyecto());
    }

    public Flux<Usuario> buscarUsuariosProyecto(String proyecto) {
        return this.usuarioProyectoService.findByProyecto(proyecto)
                .concatMap(this::buildUsuario);
    }

    private Mono<Usuario> buildUsuario(final UsuarioProyecto usuarioProyecto){
        return this.usuarioRepository.findById(usuarioProyecto.getUsuario());
    }

    public Flux<String> buscarIdsUsuariosProyecto(String proyecto) {
        return this.usuarioProyectoService.findByProyecto(proyecto)
                .concatMap(this::buildIdUsuario);
    }

    private Mono<String> buildIdUsuario(final UsuarioProyecto usuarioProyecto){
        return this.usuarioRepository.findById(usuarioProyecto.getUsuario())
                .map(retorno -> retorno.getId());
    }

    public void guardar (final Proyecto proyecto) {
        /* Si el id del proyecto es nulo o es blanco significa que es un alta de proyecto. Entonces
           se graba el proyecto y el registro de usuario-proyecto para asignárselo al usuario (manager)
           que lo da de ALTA */
        if (proyecto.getId() == null || proyecto.getId().isBlank()) {
            this.proyectoRepository
                    .save(proyecto)
                    .handle((document, sink) -> {
                        this.usuarioProyectoService
                                .save(new UsuarioProyecto(null, document.getManagerId(), document.getId()))
                                .subscribe();
                    })
                    .subscribe();
            /* En otro caso es que es una MODIFICACIÓN y se únicamente modifica el proyecto */
        } else {
            this.proyectoRepository
                    .save(proyecto)
                    .subscribe();
        }
    }

    public Mono<Proyecto> save(Proyecto proyecto) {
        return this.proyectoRepository.save(proyecto);
    }

}
