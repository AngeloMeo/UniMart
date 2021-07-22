const INT_PATTERN = /^\d+$/;
const DOUBLE_PATTERN = /^(-)?(\d+)(\.\d+)?$/;
const USERNAME_PATTERN = /^([a-zA-Z\s]){6,}$/;
const EMAIL_PATTERN = /^[a-zA-Z0-9.!#$%&’*+/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
const PASSWORD_PATTERN = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
const CODICE_FISCALE_PATTERN = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
const PHONE_PATTERN = /^\d{10}$/;

var formElement = document.getElementById('form');

formElement.addEventListener('submit', function (event)
{
    if (check())
        event.preventDefault();
    else
        formElement.submit();
});

function required(element)
{
    let results = element.value != null && element.value !== '';

    colorElement(results, element);

    return results;
}

function assertMatch(element, regexp, msg)
{
    let results = required(element) && regexp.test(element.value);

    colorElement(results, element);

    return results;
}

function colorElement(results, element)
{
    if(results)
        ok(element);
    else
        error(element);
}

function assertInt(element, msg)
{
    return assertMatch(element, INT_PATTERN, msg);
}

function assertPassword(element, msg)
{
    return assertMatch(element, PASSWORD_PATTERN, msg);
}

function assertDouble(element, msg)
{
    return assertMatch(element, DOUBLE_PATTERN, msg);
}

function assertEmail(element, msg)
{
    return assertMatch(element, EMAIL_PATTERN, msg);
}

function assertPhone(element, msg)
{
    return assertMatch(element, PHONE_PATTERN, msg);
}

function assertUsername(element, msg)
{
    return assertMatch(element, USERNAME_PATTERN, msg);
}

function assertCF(element, msg)
{
    return assertMatch(element, CODICE_FISCALE_PATTERN, msg);
}

function error(element)
{
    element.style.border = '2px solid var(--warning)';
}

function ok(element)
{
    element.style.border = '4px solid var(--secondary)';
}