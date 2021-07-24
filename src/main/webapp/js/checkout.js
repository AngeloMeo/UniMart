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
                    const obj = JSON.parse(data);
                    ok(caller);
                }
                else
                    error(caller);
            });
    }
    else
        alert(message);
}