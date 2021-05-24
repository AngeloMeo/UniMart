var modal = document.getElementById('creaCoupon');
var idCoupon = null;

window.onclick = function(event) {
    if (event.target == modal)
    {
        modal.style.display = "none";
        document.getElementById('btn1').style.display = 'block';
    }
}

function modifyForCreaCoupon() {
    document.getElementById('creaCoupon').style.display = 'block';
    document.getElementById('btn1').style.display = 'none';
    createBtn();
}

function modifyForUpdateCoupon(id, sconto, cf_creatore) {
    idCoupon = id;
    document.getElementById('sconto').value = sconto;
    document.getElementById('CF_Creatore').value = cf_creatore;
    document.getElementById('creaCoupon').style.display = 'block';
    document.getElementById('btn1').style.display = 'none';
    createBtn();
}

function createBtn(){
    if (idCoupon != null)
    {
        document.getElementById("btnDiv").innerHTML =
            '<button type="submit" class="button" formaction="./updateCoupon">Salva Modifiche</button>' +
            '<button type="submit" class="button" formaction="./deleteCoupon">Elimina Coupon</button>';
        document.getElementById("idCoupon").value = idCoupon;
        idCoupon = null;
    }
    else
        document.getElementById("btnDiv").innerHTML = '<button type="submit" id="btn2" formaction="./creaCoupon">Crea Coupon</button>';
}