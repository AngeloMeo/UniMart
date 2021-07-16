$(document).ready(
    function () {
        $("#add2cart").click(
            function () {
                $.post(getPageContext() + "/CarrelloManager/add2cart",
                    {

                        IAN: $(this).siblings("#IAN").text().replace('CodiceIAN: ', ''),
                        quantity: $(this).siblings("#quantity").val()
                    },
                    function (data, status) {
                        if(status == 200)
                            alert("t'appost")
                    });
            }
        )
    });