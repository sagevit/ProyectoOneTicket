package es.jmdedios.proyectooneticket.utilities;

import lombok.Getter;

@Getter
public enum RolesEnum {

    ADMIN("Administrador"),
    MANAGER("Manager"),
    DEVELOPER("Desarrollador"),
    USER("Usuario");

    final private String descripcion;

    RolesEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
