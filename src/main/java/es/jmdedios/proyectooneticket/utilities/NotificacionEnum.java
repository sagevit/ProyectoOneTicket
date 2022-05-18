package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum NotificacionEnum {

    NUEVO("Nuevo ticket pendiente de asignar"),
    ASIGNACION("Nuevo ticket asignado"),
    ACTUALIZACION("Actualización del ticket");

    private String descripcion;

    NotificacionEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
