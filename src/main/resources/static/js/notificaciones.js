
function Notificaciones () {

    this.source = null;

    this.start = function () {

        const $template = document.querySelector("#toastTemplate");
        const $contenedor = document.querySelector("#toastContainer");

        var loggedId = $('#loggedId').html();
        var sourceUrl = '/notificaciones/'+loggedId;

        this.source = new EventSource(sourceUrl);

        this.source.addEventListener("message", function (event) {
            var notificacion =JSON.parse(event.data)
            console.log(notificacion);

            const $import = document.importNode($template.content, true);
            const $toastItem = $import.querySelector(".toast");

            const $fecha = $import.querySelector("#fecha");

            var tiempo1tmp = Date.parse(notificacion.createdDate);
            var tiempo2tmp = Date.now();
            var dif = (tiempo2tmp - tiempo1tmp) / 1000;
            var textoDif = "Hace ";
            if (dif < 60) {
                textoDif += dif + ' segundos';
            } else if (dif < 3600) {
                dif = dif / 60;
                textoDif += Math.trunc(dif) + ' minutos';
            } else if (dif < 86400) {
                dif = dif / 3600;
                textoDif += Math.trunc(dif) + ' horas';
            } else {
                dif = dif / 86400;
                textoDif += Math.trunc(dif) + ' días';
            }
            $fecha.textContent = textoDif;

            const $a = $import.querySelector("#link");
            $a.setAttribute('href', notificacion.urlLink);
            $a.textContent = notificacion.mensaje;

            $contenedor.appendChild($toastItem);
        });

        this.source.onerror = function () {
            this.close();
        };

    };

    this.stop = function() {
        this.source.close();
    }
}

notificaciones = new Notificaciones();

window.onload = function() {
    notificaciones.start();
};

window.onbeforeunload = function() {
    notificaciones.stop();
};

