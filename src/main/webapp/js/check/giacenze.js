function check()
{
    let element = document.getElementsByClassName('giacenza');
    let length = element.length;

    for (let i = 0; i < length; i++)
        assertDouble(element[i], 'Formato Giacenza non valido');
}