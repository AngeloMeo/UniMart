$(document).ready(
    function () {
        $("#add2cart").click(
            function () {
                $.post("./CarrelloManager/add2cart",
                    {
                        /* Potevo usare anche l'ID del nodo su cui è stato fatto click,
                        * ma per motivi esercitativi faccio in due modi */
                        IAN: $(this).siblings("#IAN").text(),
                        quantity: $(this).siblings("#quantity").text()
                    },
                    function (data, status) {
                        var obj = JSON.parse(data);
                        $("#" + obj.CF + ">div").html(obj.tipoUtente);
                    });
            }
        )
    });