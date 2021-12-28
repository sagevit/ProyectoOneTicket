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
@Document(collection = "proyectos")
public class Proyecto {

    @Id
    private String id;
    private String codigo;
    private String nombre;
    private String descripcion;

}
