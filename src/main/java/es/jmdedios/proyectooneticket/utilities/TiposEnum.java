package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum TiposEnum {

    TAREA("Tarea"),
    ERRORES("Errores"),
    PETICION("Petición"),
    SOPORTE("Soporte");

    private String descripcion;

    TiposEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
