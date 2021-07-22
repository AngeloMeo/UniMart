function add(ianVar, caller)
{
    let quantityVar = caller.parentElement.firstElementChild.valueAsNumber;

    if(quantityVar == null)
        quantityVar = 1;

    $(".add2cart").html("Aggiunto al carrello")
    $(".add2cart").attr("disabled", "disabled");

    setTimeout(function() {
        $(".add2cart").html("Aggiungi al carrello")
        $(".add2cart").removeAttr("disabled");
    }, 1500);

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
