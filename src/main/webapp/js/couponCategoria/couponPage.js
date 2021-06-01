function modifyForUpdateCoupon(id, sconto, cf_creatore) {
    obj = id;
    document.getElementById('sconto').value = sconto;
    document.getElementById('CF_Creatore').value = cf_creatore;
    document.getElementById('creaModal').style.display = 'block';
    document.getElementById('btn1').style.display = 'none';
    createBtn();
}

function createBtn(){
    if (obj != null)
    {
        document.getElementById("btnDiv").innerHTML =
            '<button type="submit" class="button" formaction="CouponManager/updateCoupon">Salva Modifiche</button>' +
            '<button type="submit" class="button" formaction="CouponManager/deleteCoupon">Elimina Coupon</button>';
        document.getElementById("idCoupon").value = obj;
        obj = null;
    }
    else
        document.getElementById("btnDiv").innerHTML = '<button type="submit" id="btn2" formaction="CouponManager/creaCoupon">Crea Coupon</button>';
}