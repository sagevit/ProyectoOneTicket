package es.jmdedios.proyectooneticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "proyectos")
public class Proyecto {

    @Id
    private String id;

    @Indexed(name="unique_codigo_index", unique = true)
    @NotNull(message = "{proyecto.codigo.null}")
    @NotBlank(message = "{proyecto.codigo.blank}")
    private String codigo;

    @NotNull(message = "{proyecto.nombre.null}")
    @NotBlank(message = "{proyecto.nombre.blank}")
    private String nombre;

    private String descripcion;

    @Transient
    private String id_manager;

}
