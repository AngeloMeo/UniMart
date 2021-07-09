$(document).ready(function(){
    $(".numeroOrdine").click(function(){
        if($(this).text())
            $(window.location).attr('href', "./OrdiniManager/getOrdine?id=" + $(this).text());
    });
});

$(document).ready(function(){
    $(".deleteBtn").click(function(){
        if (confirm("Vuoi eliminare tale ordine ?"))
        {
            $.post("./OrdiniManager/deleteOrdine",
                {
                    id: $(this).attr('value')
                },
                function (data, status)
                {
                    if(status == 'success')
                        alert(data);
                    else
                        alert(data);

                    location.reload();
                }
            )
        }
        else
        {
            alert('Nessuna modifica effettuata');
        }
    });
});