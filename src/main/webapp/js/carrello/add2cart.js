function add(ianVar, caller)
{
    let quantityVar = caller.parentElement.firstElementChild.value;

    //if(!assertDouble(caller.parentElement.firstElementChild, "Sei simpatico"))
    if(quantityVar == null ||  typeof quantityVar != 'number'){
        quantityVar = 1;
        $(".add2cart").html("Aggiunta 1 unità")
    }
    else
        $(".add2cart").html("Aggiunto al carrello")

    $(".add2cart").attr("disabled", "disabled");

    setTimeout(function() {
        $(".add2cart").html("Aggiungi al carrello")
        $(".add2cart").removeAttr("disabled");
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
