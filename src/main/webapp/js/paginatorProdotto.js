function getItems(size, offset)
{
    $.get(
        'ProdottoManager/',
        {
            size: size,
            offset: offset
        },
        function (data)
        {
            if(data.trim() !== '')
            {
                aggiornaOffset();
                $('.table').append(data);
            }
        }
    );
}