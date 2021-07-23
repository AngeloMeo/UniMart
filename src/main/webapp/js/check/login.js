function check()
{
    let element = document.getElementById('usernameEmail');

    if(!assertUsername(element, 'Formato Username non corretto'))
    {
        flag = false;
        assertEmail(element, 'Formato Email non corretto');
    }

    assertPassword(document.getElementById('password'), 'Formato Password non corretto');
}

function registrazione()
{
    window.location = getPageContext() + "/UtenteManager/creaUtente";
}