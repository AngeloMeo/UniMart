$(document).ready(
    function () {
        const regex = /[^\s]/;

        $('#searchBar').bind('input', function () {
            if(regex.test($(this).val()))
            {
                $.get(
                    'SearchManager/',
                    {
                        text: $(this).val()
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
                                $('#resultsSearch').append(obj['categorie'][i] + "<br>");
                            }

                            for(let i in obj['prodotti'])
                            {
                                $('#resultsSearch').append(obj['prodotti'][i] + "<br>");
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