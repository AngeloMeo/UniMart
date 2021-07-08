$(document).ready(function(){
    $("fieldset").click(function(){
        if($(this).attr('id'))
            $(window.location).attr('href', "./SearchManager/prodotto?id=" + $(this).attr('id'));
    });
});