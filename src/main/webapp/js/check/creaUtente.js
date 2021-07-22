var formElement = document.getElementById('form');

formElement.addEventListener('submit', function (event)
    {
        if (check())
            event.preventDefault();
        else
            formElement.submit();
    });

function check()
{
    let flag = false;

    let element = document.getElementById('CF');
    reset(element);

    if(!assertCF(element.value, 'Formato CF non valido'))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('nome');
    reset(element);

    if(!required(element.value))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('cognome');
    reset(element);

    if(!required(element.value))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('viaCivico');
    reset(element);

    if(!required(element.value))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('citta');
    reset(element);

    if(!required(element.value))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('regione');
    reset(element);

    if(!required(element.value))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('telefono');
    reset(element);

    if(!assertPhone(element.value, 'Formato Telefono non corretto'))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('email');
    reset(element);

    if(!assertEmail(element.value, 'Formato Email non corretto'))
    {
        flag = true;
        error(element);
    }

    element = document.getElementById('username');
    reset(element);

    if(!assertUsername(element.value, 'Formato Username non corretto'))
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

function error(element)
{
    element.style.border = '2px solid var(--warning)';
}

function reset(element)
{
    element.style.border = '4px solid var(--secondary)';
}