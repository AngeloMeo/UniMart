$(document).ready(
    function () {
        $(".add2cart").click(
            function () {
                let quantityVar = $(this).siblings("#quantity").val();
                let element = $(this);

                if(quantityVar == null)
                    quantityVar = 1;

                $.post(getPageContext() + "/CarrelloManager/add2cart",
                    {
                        IAN: element.siblings("#IAN").text().replace('CodiceIAN: ', ''),
                        quantity: quantityVar
                    },
                    function (data, status) {
                        if(status == 'success')
                        {
                            console.log(element.siblings("#IAN").text().replace('CodiceIAN: ', ''));
                            $(element).addClass('hide');
                            $(element).next('button').removeClass('hide');
                        }
                    });
            }
        )
    });