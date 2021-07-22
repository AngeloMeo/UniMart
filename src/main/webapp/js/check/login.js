function check()
{
    let flag = false;

    element = document.getElementById('usernameEmail');
    reset(element);

    if(!(assertUsername(element.value, 'Formato Username non corretto') || assertEmail(element.value, 'Formato Email non corretto')))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('password');
    reset(element);

    if(!assertPassword(element.value, 'Formato Password non corretto'))
    {
        flag = true;
        error(element);
    }

    return flag;
}