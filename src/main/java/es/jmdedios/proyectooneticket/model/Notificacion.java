package es.jmdedios.proyectooneticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "notificaciones")
public class Notificacion {

    @Id
    private String id;

    private String ticketId;

    @NotNull(message = "{notificacion.mensaje.null}")
    private String mensaje;

    @NotNull(message = "{notificacion.mensaje.null}")
    private String urlLink;

    private String usuarioId;

    private Boolean visible;

    @CreatedDate
    private Date createdDate;

}
