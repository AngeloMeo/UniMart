function add(ianVar, caller)
{
    let quantityVar = caller.parentElement.firstElementChild.value;

    //if(!assertDouble(caller.parentElement.firstElementChild, "Sei simpatico"))
    if(quantityVar == null ||  typeof quantityVar != 'number'){
        quantityVar = 1;
        $(caller).html("Aggiunta 1 unità")
    }
    else
        $(caller).html("Aggiunto al carrello")

    $(caller).attr("disabled", "disabled");

    setTimeout(function() {
        $(caller).html("Aggiungi al carrello")
        $(caller).removeAttr("disabled");
    }, 1000);

    $.post(getPageContext() + "/CarrelloManager/add2cart",
        {
            IAN: ianVar,
            quantity: quantityVar
        },
        function (data, status) {
            if(status == 'success')
            {

            }
        });
}
