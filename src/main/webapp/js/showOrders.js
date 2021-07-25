$(document).ready(function(){
    $(".numeroOrdine").click(function(){
        if($(this).text())
            $(window.location).attr('href', "./OrdiniManager/getOrdine?id=" + $(this).text());
    });
});

$(document).ready(function(){
    $(".deleteBtn").click(function(){
        if (confirm("Vuoi eliminare quest'ordine ?"))
        {
            caller = $(this);

            $.post("./OrdiniManager/deleteOrdine",
                {
                    id: caller.attr('value')
                },
                function (data, status)
                {
                    if(status == 'success')
                    {
                        document.getElementById('stato'+ caller.attr('value')).innerHTML = "Annullato";
                        $(caller).remove();
                    }

                    alert(data);
                }
            )
        }
        else
        {
            alert('Nessuna modifica effettuata');
        }
    });
});