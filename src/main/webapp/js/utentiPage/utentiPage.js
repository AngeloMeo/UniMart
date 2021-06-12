$(document).ready(
    function () {
        $(".tipoUtente").click(
            function () {
                $.post("./UtenteManager/modificaTipo",
                    {
                        /* Potevo usare anche l'ID del nodo su cui è stato fatto click,
                        * ma per motivi esercitativi faccio in due modi */
                        cfUtente: $(this).siblings(".cfUtente").text()
                    },
                    function (data, status) {
                        var obj = JSON.parse(data);
                        $("#" + obj.CF + ">div").html(obj.tipoUtente);
                    });
            }
        )
    });