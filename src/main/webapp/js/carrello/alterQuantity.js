function alter(caller, ianVar)
{
    let quantityVar = caller.parentElement.firstElementChild.valueAsNumber;


    if(quantityVar != 0)
        $.post(getPageContext() + "/CarrelloManager/alterquantities", {
                IAN: ianVar,
                quantity: quantityVar
            },
            function (data, status) {
                if(status == 'success') {
                    const obj = JSON.parse(data)
                    $("#price"+ianVar).html((obj.prezzo * obj.quantita).toFixed(2) + "&euro; <br> ("+obj.prezzo+"&euro;/Unità)")
                }
            });
    else{
        del(ianVar)
    }
}

function del(ian)
{

    $.post(getPageContext() + "/CarrelloManager/alterquantities", {
            IAN: ian,
            quantity: 0
        },
        function (data, status) {
            if(status == 'success'){
                $("#"+ian).remove()
            }
        }
        )

}