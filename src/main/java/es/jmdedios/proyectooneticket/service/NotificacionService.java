package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Notificacion;
import es.jmdedios.proyectooneticket.repository.INotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NotificacionService {

    @Autowired
    INotificacionRepository notificacionRepository;

    public Flux<Notificacion> findWithTailableCursorByUsuarioId(String usuario, Boolean visible) {
        return this.notificacionRepository.findWithTailableCursorByUsuarioIdAndVisible(usuario, visible);
    }

    public Mono<Notificacion> guardar(Notificacion notificacion) {
        return this.notificacionRepository.save(notificacion);
    }

}
