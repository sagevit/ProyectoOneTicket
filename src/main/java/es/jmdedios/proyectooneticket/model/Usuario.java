package es.jmdedios.proyectooneticket.model;

import es.jmdedios.proyectooneticket.utilities.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    @Indexed(name="unique_codigo_index", unique = true)
    @NotNull(message = "{usuario.codigo.null}")
    @NotBlank(message = "{usuario.codigo.blank}")
    private String codigo;

    @NotNull(message = "{usuario.nombre.null}")
    @NotBlank(message = "{usuario.nombre.blank}")
    private String nombre;

    @Email
    @NotNull(message = "{usuario.email.null}")
    @NotBlank(message = "{usuario.email.blank}")
    private String email;

    @NotNull(message = "{usuario.rol.null}")
    private RolesEnum rol;

}
