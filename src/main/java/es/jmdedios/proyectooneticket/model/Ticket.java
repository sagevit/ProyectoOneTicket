package es.jmdedios.proyectooneticket.model;

import es.jmdedios.proyectooneticket.dtopattern.TicketDTO;
import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import es.jmdedios.proyectooneticket.utilities.PrioridadEnum;
import es.jmdedios.proyectooneticket.utilities.TiposEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

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

    private PrioridadEnum prioridad;

    private EstadosEnum estado;

    private Integer realizado;

    private String descripcion;

    //fecha de inicio
    private LocalDate fechaCreacion;

    //fecha de fin
    private LocalDate fechaFinalizacion;

    //Usuario de creación (manager y usuario)
    private String propietarioId;

    //Usuario asignado (manager y developer)
    private String asignadoId;

    @Transient
    public static final String SEQUENCE_NAME = "tickets_sequence";

    public Ticket (TicketDTO ticketDTO) {
        if (!ticketDTO.getTicketId().isBlank()) {
            this.id = ticketDTO.getTicketId();
        }
        this.proyectoId = ticketDTO.getProyectoId();
        this.secuencia = ticketDTO.getSecuencia();
        this.asunto = ticketDTO.getAsunto();
        this.tipo = ticketDTO.getTipo();
        this.prioridad = ticketDTO.getPrioridad();
        this.estado = ticketDTO.getEstado();
        this.realizado = ticketDTO.getRealizado();
        this.descripcion = ticketDTO.getDescripcion();
        this.propietarioId = ticketDTO.getPropietarioId();
        if (Objects.isNull(ticketDTO.getAsignadoId()) || ticketDTO.getAsignadoId().isBlank()) {
            this.asignada = false;
        } else {
            this.asignada = true;
            this.asignadoId = ticketDTO.getAsignadoId();
        }
        if (Objects.isNull(ticketDTO.getFechaCreacion())) {
            this.fechaCreacion = LocalDate.now();
        } else {
            this.fechaCreacion = ticketDTO.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (!Objects.isNull(ticketDTO.getFechaFinalizacion())) {
            this.fechaFinalizacion = ticketDTO.getFechaFinalizacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

}
