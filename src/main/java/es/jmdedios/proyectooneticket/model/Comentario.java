package es.jmdedios.proyectooneticket.model;

import es.jmdedios.proyectooneticket.utilities.EstadosEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "comentarios")
public class Comentario {

    @Id
    private String id;

    @Indexed(name="unique_secuencia_index", unique = true)
    private long secuencia;

    private String ticketId;

    @NotNull(message = "{comentario.estado.null}")
    private EstadosEnum estado;

    @Min(value = 1, message = "{comentario.realizado.min}")
    @Max(value = 100, message = "{comentario.realizado.max}")
    private Integer realizado;

    private String comentario;

    private String usuarioId;

    private LocalDate fechaCreacion;

    @Transient
    public static final String SEQUENCE_NAME = "comentarios_sequence";

    public Comentario (Mono<Ticket> ticket) {
        ticket.subscribe(t -> {
            this.ticketId = t.getId();
            this.estado = t.getEstado();
            if (t.getRealizado() == null || t.getRealizado() == 0) {
                this.realizado = 1;
            } else {
                this.realizado = t.getRealizado();
            }
        });
    }

}
