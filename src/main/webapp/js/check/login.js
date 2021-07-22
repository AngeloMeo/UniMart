function check()
{
    let flag = false;

    let element = document.getElementById('usernameEmail');

    if(!assertUsername(element, 'Formato Username non corretto'))
        if(!assertEmail(element, 'Formato Email non corretto'))
            flag = true;

    element = document.getElementById('password');

    if(!assertPassword(element, 'Formato Password non corretto'))
        flag = true;

    return flag;
}