$(document).ready(function(){
    $("fieldset").click(function(){
        if($(this).attr('id'))
        {
            var index = window.location.href.indexOf('/SearchManager');
            var context = '';

            if(index != -1)
                context = window.location.href.substring(0, index);

            $(window.location).attr('href', context + "/SearchManager/prodotto?id=" + $(this).attr('id'));
        }
    });
});