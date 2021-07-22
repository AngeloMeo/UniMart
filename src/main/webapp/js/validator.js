const INT_PATTERN = /^\d+$/;
const DOUBLE_PATTERN = /^(-)?(\d+)(\.\d+)?$/;
const USERNAME_PATTERN = /^([a-zA-Z\s]){6,}$/;
const EMAIL_PATTERN = /^[a-zA-Z0-9.!#$%&’*+/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
const PASSWORD_PATTERN = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
const CODICE_FISCALE_PATTERN = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
const PHONE_PATTERN = /^\d{10}$/;

function required(value)
{
    value = value.trim();

    return value != null && value != '';
}

function assertMatch(value, regexp, msg)
{
    return  required(value) && regexp.test(value);
}

function assertInt(value, msg)
{
    return assertMatch(value, INT_PATTERN, msg);
}

function assertPassword(value, msg)
{
    return assertMatch(value, PASSWORD_PATTERN, msg);
}

function assertDouble(value, msg)
{
    return assertMatch(value, DOUBLE_PATTERN, msg);
}

function assertEmail(value, msg)
{
    return assertMatch(value, EMAIL_PATTERN, msg);
}

function assertPhone(value, msg)
{
    return assertMatch(value, PHONE_PATTERN, msg);
}

function assertUsername(value, msg)
{
    return assertMatch(value, USERNAME_PATTERN, msg);
}

function assertCF(value, msg)
{
    return assertMatch(value, CODICE_FISCALE_PATTERN, msg);
}

var formElement = document.getElementById('form');

formElement.addEventListener('submit', function (event)
{
    if (check())
        event.preventDefault();
    else
        formElement.submit();
});

function error(element)
{
    element.style.border = '2px solid var(--warning)';
}

function reset(element)
{
    element.style.border = '4px solid var(--secondary)';
}