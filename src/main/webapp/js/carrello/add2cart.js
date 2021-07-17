function add(ianVar, caller) {
    let quantityVar = caller.parentElement.firstElementChild.valueAsNumber;

    if(quantityVar == null)
        quantityVar = 1;

    $.post(getPageContext() + "/CarrelloManager/add2cart",
        {
            IAN: ianVar,
            quantity: quantityVar
        },
        function (data, status) {
            if(status == 'success')
            {
                caller.classList.add('hide');
                caller.nextElementSibling.classList.remove('hide');
            }
        });
}
