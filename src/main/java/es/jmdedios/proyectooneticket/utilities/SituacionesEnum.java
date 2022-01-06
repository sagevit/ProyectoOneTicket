package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum SituacionesEnum {

    PENDIENTE("Pendiente de asignar"),
    ASIGNADA("Asignada");

    private String descripcion;

    SituacionesEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
