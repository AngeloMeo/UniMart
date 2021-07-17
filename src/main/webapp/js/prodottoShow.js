$(document).ready(function(){
    $("img").click(function(){
        let ianVar = $(this).siblings("#IAN").text().replace('CodiceIAN: ', '');
        console.log($(this))
        if(ianVar)
            $(window.location).attr('href', getPageContext() + "/SearchManager/prodotto?id=" + ianVar);
    });
});