package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.model.UsuarioProyecto;
import es.jmdedios.proyectooneticket.service.ProyectoService;
import es.jmdedios.proyectooneticket.service.UsuarioProyectoService;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("proyectos/asignacion")
public class AsignacionController {

    @Autowired
    ProyectoService proyectoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioProyectoService usuarioProyectoService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return this.usuarioService.getUsuario();
    }

    @GetMapping("/{id}")
    public Mono<String> main(@PathVariable String id, final Model model, Authentication auth) {
        return this.usuarioService
                .findByCodigo(auth.getName())
                .map(result -> {
                    if (result.getRol().equals(RolesEnum.ADMIN)) {
                        return "redirect:/admin";
                    } else {
                        model.addAttribute("idProyecto", id);
                        model.addAttribute("proyecto", this.proyectoService.findById(id));
                        model.addAttribute("usuariosProyecto", this.proyectoService.buscarUsuariosProyecto(id));
                        model.addAttribute("idsUsersProyecto", this.proyectoService.buscarIdsUsuariosProyecto(id));
                        model.addAttribute("usuariosSistema", this.usuarioService.findByRolNot(RolesEnum.ADMIN.toString()));
                        return "asignacion";
                    }
                });
    }

    @GetMapping("/{id}/filter")
    public String filtrado(@PathVariable String id, final Model model) {
        model.addAttribute("idProyecto", id);
        model.addAttribute("idsUsersProyecto", this.proyectoService.buscarIdsUsuariosProyecto(id));
        model.addAttribute("usuariosSistema", this.usuarioService.findByRolNot(RolesEnum.ADMIN.toString()));
        return "asignacion :: #listUsuariosSistema";
    }

    @GetMapping("/{id}/filter/{filtro}")
    public String filtrado(@PathVariable String id, @PathVariable String filtro, final Model model) {
        model.addAttribute("idProyecto", id);
        model.addAttribute("idsUsersProyecto", this.proyectoService.buscarIdsUsuariosProyecto(id));
        model.addAttribute("usuariosSistema", this.usuarioService.findByNombreContainsIgnoreCaseAndRolNot(filtro, RolesEnum.ADMIN.toString()));
        return "asignacion :: #listUsuariosSistema";
    }

    @GetMapping("/{id}/asignar/{usuario}")
    public String asignar(@PathVariable String id, @PathVariable String usuario, final Model model) {
        this.usuarioProyectoService
                .save(new UsuarioProyecto(null, usuario, id))
                .subscribe();
        model.addAttribute("idProyecto", id);
        model.addAttribute("idsUsersProyecto", this.proyectoService.buscarIdsUsuariosProyecto(id));
        model.addAttribute("usuariosProyecto", this.proyectoService.buscarUsuariosProyecto(id));
        return "asignacion :: #listUsuariosProyecto";
    }

    @GetMapping("/{id}/desasignar/{usuario}")
    public String desasignar(@PathVariable String id, @PathVariable String usuario, final Model model) {
        this.usuarioProyectoService
                .deleteByUsuarioAndProyecto(usuario, id)
                .subscribe();
        model.addAttribute("idProyecto", id);
        model.addAttribute("idsUsersProyecto", this.proyectoService.buscarIdsUsuariosProyecto(id));
        model.addAttribute("usuariosProyecto", this.proyectoService.buscarUsuariosProyecto(id));
        return "asignacion :: #listUsuariosProyecto";
    }

}
