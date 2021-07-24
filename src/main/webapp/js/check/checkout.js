function validateCoupon(caller)
{
    if(assertInt(caller, "Numero Coupon non valido"))
    {
        $.post(getPageContext() + "/CouponManager/validateCoupon",
            {
                ID: caller.value
            },
            function (data)
            {
                if(data.trim() !== 'null')
                {
                    let obj = JSON.parse(data);
                    ok(caller);

                    let totale = parseFloat(document.getElementById('totale').textContent.replace(',','.'));
                    totale = totale - ((totale * parseFloat(obj['sconto'])) / 100);

                    document.getElementById('totaleCoupon').innerText = totale.toFixed(2);
                    document.getElementById('totaleCoupon').parentElement.parentElement.removeAttribute('class');
                }
                else
                    error(caller);
            });
    }
    else
        alert(message);
}

function check()
{
    assertInt(document.getElementById('coupon'), "Numero Coupon non valido");
    required(document.getElementById('viaCivico'));
    required(document.getElementById('citta'));
    required(document.getElementById('regione'));
}