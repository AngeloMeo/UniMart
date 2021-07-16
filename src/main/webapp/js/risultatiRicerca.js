$(document).ready(function(){
    $("fieldset").click(function(){
        if($(this).attr('id'))
            $(window.location).attr('href', getPageContext() + "/SearchManager/prodotto?id=" + $(this).attr('id'));
    });
});