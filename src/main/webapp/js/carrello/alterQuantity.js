function alter(caller, ianVar)
{
    let quantityVar = caller.parentElement.firstElementChild.valueAsNumber;


    $.post(getPageContext() + "/CarrelloManager/alterquantities",
        {
            IAN: ianVar,
            quantity: quantityVar
        },
        function (data, status) {
            if(status == 'success')
            {
                const obj = JSON.parse(data)
                document.getElementById("price"+ianVar).innerHTML = (obj.prezzo * obj.quantita).toFixed(2) + "&euro; <br> ("+obj.prezzo+"&euro;/Unità)"
            }
        });
}