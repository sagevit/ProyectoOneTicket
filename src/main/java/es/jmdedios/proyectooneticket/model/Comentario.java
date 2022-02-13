package es.jmdedios.proyectooneticket.model;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "comentarios")
public class Comentario {

    @Id
    private String id;

    private String ticketId;

    private EstadosEnum estado;

    private Integer realizado;

    private String comentario;

    @Transient
    public static final String SEQUENCE_NAME = "tickets_sequence";

    public Comentario (TicketDTO ticketDTO) {
        this.ticketId = ticketDTO.getTicketId();
        this.estado = ticketDTO.getEstado();
        this.realizado = ticketDTO.getRealizado();
        this.comentario = ticketDTO.getDescripcion();
    }

}
