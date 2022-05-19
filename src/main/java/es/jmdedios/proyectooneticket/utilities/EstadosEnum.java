package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum EstadosEnum {

    INICIAL("Inicial"),
    ENCURSO("En curso"),
    PAUSADA("En pausa"),
    ESPERA("A la espera"),
    CERRADA("Cerrada");

    final private String descripcion;

    EstadosEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
