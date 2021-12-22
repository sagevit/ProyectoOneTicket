package es.jmdedios.proyectooneticket;

import es.jmdedios.proyectooneticket.model.Proyecto;
import es.jmdedios.proyectooneticket.model.Usuario;
import es.jmdedios.proyectooneticket.repository.IUsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) { SpringApplication.run(Application.class, args); }

    @Bean
    CommandLineRunner initData(IUsuarioRepository repository) {
        return (args -> {

            Proyecto proyecto1 = new Proyecto("61b4b265d03f9eed7ba3e516", "#1", "Proyecto 1", "Descripción del proyecto 1");
            Proyecto proyecto2 = new Proyecto("61b4b265d03f9eed7ba3e517", "#2", "Proyecto 2", "Descripción del proyecto 2");

            Usuario usuario = new Usuario(null, "desarrollador2", "Desarrollador 2", Arrays.asList(proyecto1, proyecto2));
            repository.insert(usuario);
        });
    }

}
