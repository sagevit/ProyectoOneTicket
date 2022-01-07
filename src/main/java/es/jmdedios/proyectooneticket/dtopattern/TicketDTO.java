package es.jmdedios.proyectooneticket.dtopattern;

import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import es.jmdedios.proyectooneticket.utilities.PrioridadEnum;
import es.jmdedios.proyectooneticket.utilities.TiposEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tickets")
public class TicketDTO {

    private String ticketId;

    @NotNull(message = "{proyecto.codigo.null}")
    @NotBlank(message = "{proyecto.codigo.blank}")
    private String proyectoId;

//    private long secuencia;

    private boolean asignada;

    @NotNull(message = "{ticket.asunto.null}")
    @NotBlank(message = "{ticket.asunto.blank}")
    private String asunto;

    @NotNull(message = "{ticket.tipo.null}")
    private TiposEnum tipo;

    @NotNull(message = "{ticket.estado.null}")
    private EstadosEnum estado;

    @NotNull(message = "{ticket.prioridad.null}")
    private PrioridadEnum prioridad;

    private Integer realizado;

    private Date fechaCreacion;

    private Date fechaFinalizacion;

    //Usuario de creación (manager y usuario)
    @NotNull(message = "{ticket.propietario.null")
    @NotBlank(message = "{ticket.propietario.blak}")
    private String propietarioId;

    //Usuario asignado (manager y developer)
    private String asignadoId;

    private String comentarioId;

    @NotNull(message = "{ticket.descripcion.null}")
    @NotBlank(message = "{ticket.descripcion.blank}")
    private String descripcion;

}
