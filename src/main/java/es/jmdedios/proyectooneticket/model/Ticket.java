package es.jmdedios.proyectooneticket.model;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import es.jmdedios.proyectooneticket.utilities.PrioridadEnum;
import es.jmdedios.proyectooneticket.utilities.SituacionesEnum;
import es.jmdedios.proyectooneticket.utilities.TiposEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;

    private String proyecto;

    @Indexed(name="unique_secuencia_index", unique = true)
    private long secuencia;

    private String asunto;

    private SituacionesEnum situacion;

    private TiposEnum tipo;

    private EstadosEnum estado;

    private PrioridadEnum prioridad;

    private Integer realizado;

    //fecha de inicio
    @CreatedDate
    private LocalDateTime fechaCreacion;

    //fecha de fin
    private LocalDateTime fechaFinalizacion;

    //Usuario de creación (manager y usuario)
    private String propietario;

    //Usuario asignado (manager y developer)
    private String asignado;

    @Transient
    public static final String SEQUENCE_NAME = "tickets_sequence";

    public Ticket (TicketDTO ticketDTO) {

    }

}
