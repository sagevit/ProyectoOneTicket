function filterBarUsuarios(idProyecto) {

    var filterUsuario = $('#filterUsuario').val();

    $.ajax({
        url: '/proyectos/asignacion/'+idProyecto+'/filter/'+filterUsuario,
        success: function (data) {
            $('#groupUsuariosSistema').html(data);
        }
    });
}