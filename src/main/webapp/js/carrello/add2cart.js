$(document).ready(
    function () {
        $("#add2cart").click(
            function () {
                $.post(getPageContext() + "/CarrelloManager/add2cart",
                    {

                        IAN: $(this).siblings("#IAN").text(),
                        quantity: $(this).siblings("#quantity").text()
                    },
                    function (data, status) {
                        if(status == 200)
                            alert("t'appost")
                    });
            }
        )
    });