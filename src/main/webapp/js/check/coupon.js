function check()
{
    let flag = false;

    let element = document.getElementById('sconto');
    reset(element);

    if(!assertDouble(element.value, 'Formato Sconto non valido'))
    {
        flag = true;
        error(element);
    }

    return flag;
}