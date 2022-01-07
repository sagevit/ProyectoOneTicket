package es.jmdedios.proyectooneticket.model;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import es.jmdedios.proyectooneticket.utilities.PrioridadEnum;
import es.jmdedios.proyectooneticket.utilities.TiposEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;

    private String proyectoId;

    @Indexed(name="unique_secuencia_index", unique = true)
    private long secuencia;

    private boolean asignada;

    private String asunto;

    private TiposEnum tipo;

    private EstadosEnum estado;

    private PrioridadEnum prioridad;

    private Integer realizado;

    //fecha de inicio
    private Date fechaCreacion;

    //fecha de fin
    private Date fechaFinalizacion;

    //Usuario de creación (manager y usuario)
    private String propietarioId;

    //Usuario asignado (manager y developer)
    private String asignadoId;

    //Datos de auditoria
    @CreatedDate
    private Instant createdDate;

    @CreatedBy
    private String createdBy;

    @Transient
    public static final String SEQUENCE_NAME = "tickets_sequence";

    public Ticket (TicketDTO ticketDTO) {
        this.proyectoId = ticketDTO.getProyectoId();
        this.asignada = false;
        this.asunto = ticketDTO.getAsunto();
        this.tipo = ticketDTO.getTipo();
        this.estado = ticketDTO.getEstado();
        this.prioridad = ticketDTO.getPrioridad();
        this.realizado = 0;
        this.propietarioId = ticketDTO.getPropietarioId();
        this.fechaCreacion = new Date();
    }

}
