package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum TiposEnum {

    SOPORTE("Soporte"),
    ERRORES("Errores"),
    TAREA("Tarea"),
    PETICION("Petición"),
    EVOLUTIVO("Evolutivo");

    private String descripcion;

    TiposEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
