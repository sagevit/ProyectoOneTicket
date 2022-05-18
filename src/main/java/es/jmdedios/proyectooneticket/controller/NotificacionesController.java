package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Notificacion;
import es.jmdedios.proyectooneticket.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("notificaciones")
public class NotificacionesController {

    @Autowired
    NotificacionService notificacionService;

    @ResponseBody // The response payload for this request will be rendered in JSON, not HTML
    @RequestMapping(
            value = "/{usuarioId}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notificacion> notificacionesStream(
            @PathVariable String usuarioId) {
        return this.notificacionService.findWithTailableCursorByUsuarioId(usuarioId, true);

    }
}
