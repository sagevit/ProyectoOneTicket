package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum RolesEnum {

    ADMIN("Administrador"),
    MANAGER("Manager"),
    DEVELOVER("Desarrollador"),
    USER("Usuario");

    private String descripcion;

    private RolesEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
