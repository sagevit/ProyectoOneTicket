package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Notificacion;
import es.jmdedios.proyectooneticket.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
@RequestMapping("notificaciones")
public class NotificacionesController {

    @Autowired
    NotificacionService notificacionService;

    @ResponseBody // The response payload for this request will be rendered in JSON, not HTML
    @RequestMapping(
            value = "/{usuarioId}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notificacion> notificacionesStream(@PathVariable String usuarioId) {
        return this.notificacionService.findWithTailableCursorByUsuarioId(usuarioId, true).delayElements(Duration.ofMillis(500));
    }

    @GetMapping("/update/{id}")
    public String invisibilizar(@PathVariable String id, @RequestParam String urlLink, final Model model) {
        this.notificacionService.updateVisibilidadNotificacion(id, Boolean.FALSE);
        return "redirect:"+urlLink;
    }
}
