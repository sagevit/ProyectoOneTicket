function onChangeCBUsuario(id_usuario) {

    var accion;

    if (idsUsersProyecto.includes(id_usuario)) {
        accion = '/desasignar/'
    } else {
        accion = '/asignar/'
    }

    $.ajax({
        url: id_proyecto+accion+id_usuario,
        success: function (data) {
            $('#groupUsuariosProyecto').html(data);
        }
    });
}