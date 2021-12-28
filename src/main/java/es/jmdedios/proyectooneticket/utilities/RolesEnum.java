package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum RolesEnum {

    BLANK("Sin rol"),
    MANAGER("Manager"),
    DEVELOVER("Desarrollador"),
    USER("Usuario");

    private String descripcion;

    private RolesEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
