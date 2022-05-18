package es.jmdedios.proyectooneticket.service;

import es.jmdedios.proyectooneticket.model.Notificacion;
import es.jmdedios.proyectooneticket.repository.INotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
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

    public void updateVisibilidadNotificacion(String id, Boolean visible) {
        this.notificacionRepository.findById(id).subscribe(n -> {
            n.setVisible(Boolean.FALSE);
            this.notificacionRepository.save(n).subscribe();
        });
    };

}
