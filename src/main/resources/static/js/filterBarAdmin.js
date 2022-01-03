function filterBarAdmin() {

    var filterNombre = $('#filterNombre').val();
    var filterRol = $('#filterRol').val();
    var url = 'admin/filter';
    if (filterNombre || filterRol ){
        if (!filterNombre) {
            filterNombre = '-';
        }
        if (!filterRol) {
            filterRol = '-';
        }
        url += '/' + filterNombre + '&' + filterRol;
    }
    $('#listUsuarios').load(url);
}