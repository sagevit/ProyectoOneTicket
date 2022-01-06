package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum EstadosEnum {

    INICIAL("Inicial"),
    ENCURSO("En curso"),
    PAUSADA("En pausa"),
    CERRADA("Cerrada");

    private String descripcion;

    EstadosEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
