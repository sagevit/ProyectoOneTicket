package es.jmdedios.proyectooneticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "usuarios_proyectos")
public class UsuarioProyecto {

    @Id
    private String id;

    private String id_usuario;

    private String id_proyecto;

}
