package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum PrioridadEnum {

    BAJA("Baja"),
    NORMAL("Normal"),
    ALTA("Alta");

    private String descripcion;

    PrioridadEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
