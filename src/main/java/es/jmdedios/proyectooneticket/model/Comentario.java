package es.jmdedios.proyectooneticket.model;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import es.jmdedios.proyectooneticket.utilities.PrioridadEnum;
import es.jmdedios.proyectooneticket.utilities.SituacionesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tickets")
public class Comentario {

    @Id
    private String id;

    private String ticketId;

    private EstadosEnum estado;

    private Integer realizado;

    private String descripcion;

    @Transient
    public static final String SEQUENCE_NAME = "tickets_sequence";

    public Comentario (TicketDTO ticketDTO) {

    }

}
