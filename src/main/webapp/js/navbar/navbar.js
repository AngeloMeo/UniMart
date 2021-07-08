$(document).ready(
    function () {
        const regex = /[^\s]/;

        $('#searchBar').bind('input', function () {
            let text = $.trim($(this).val());

            if(regex.test(text))
            {
                $.get(
                    'SearchManager/',
                    {
                        text: text
                    },
                    function (data)
                    {
                        var obj = JSON.parse(data);

                        $('#resultsSearch').empty();
                        $('#resultsSearch').css("display", "block");
                        $('#resultsSearch').css("border", "1px solid #A5ACB2");
                        $('#resultsSearch').css("backgroundColor", "white");

                        if(obj['default'] == null)
                        {
                            for(let i in obj['categorie'])
                            {
                                let text = obj['categorie'][i];

                                $('#resultsSearch').append("<a href='SearchManager/categoria?id=" + text + "'>" + text + "</a><br>");
                            }

                            for(let i in obj['prodotti'])
                            {
                                let text = obj['prodotti'][i];
                                let key = text.substring(text.indexOf(':') + 2);
                                $('#resultsSearch').append("<a href='SearchManager/prodotto?id=" + key + "'>" + text + "</a><br>");
                            }
                        }
                        else
                            $('#resultsSearch').append(obj['default'] + "<br>");
                    }
                );
            }
            else
            {
                $('#resultsSearch').empty();
                $('#resultsSearch').css("display", "none");
            }
        });
    });