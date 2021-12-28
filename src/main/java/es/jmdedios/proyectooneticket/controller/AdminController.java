package es.jmdedios.proyectooneticket.controller;

import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.service.UsuarioService;
import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UsuarioService usuarioService;

    @ModelAttribute("logged")
    public Mono<Usuario> userLogged() {
        return usuarioService.getUsuario();
    }

    @GetMapping("")
    public String index(Authentication auth, final Model model) {
        if (!auth.getName().equals("admin")) {
            return "redirect:/";
        }
        model.addAttribute("usuarios", usuarioService.findAll());
        return "admin";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, final Model model) {
        model.addAttribute("usuario", usuarioService.findById(id));
        return "formUsuario";
    }

    @GetMapping("/nuevo")
    public String nuevo(final Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formUsuario";
    }

    @PostMapping("/grabar")
    public String submit(@Valid Usuario usuario, Errors errores, final Model model) {
        if (errores.hasErrors()) {
            return "formUsuario";
        }
        usuarioService.save(usuario).subscribe();
        return "redirect:/admin";
    }
}
