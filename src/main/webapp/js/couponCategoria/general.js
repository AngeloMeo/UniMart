var modal = document.getElementById('creaModal');
var obj = null;

window.onclick = function(event) {
    if (event.target == modal)
    {
        modal.style.display = "none";
        document.getElementById('btn1').style.display = 'block';
    }
}

function modifyForCrea() {
    document.getElementById('creaModal').style.display = 'block';
    document.getElementById('btn1').style.display = 'none';
    createBtn();
}