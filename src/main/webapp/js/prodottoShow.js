$(document).ready(function(){
    $("img").click(function(){
        let ianVar = $(this).siblings("#IAN").text().replace('CodiceIAN: ', '');

        if(ianVar)
            $(window.location).attr('href', getPageContext() + "/SearchManager/prodotto?id=" + ianVar);
    });
});