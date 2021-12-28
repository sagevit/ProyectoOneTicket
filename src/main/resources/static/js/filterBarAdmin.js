function filterBarAdmin() {

    var filterCodigo = $('#filterCodigo').val();
    var filterRol = $('#filterRol').val();
    var url = 'admin/filter'
    if (filterCodigo || filterRol ){
        if (!filterCodigo) {
            filterCodigo = '-';
        }
        if (!filterRol) {
            filterRol = '-';
        }
        url += '/' + filterCodigo + '&' + filterRol;
    }
    $('#listUsuarios').load(url);
}