function modifyForOrdine(id, feedback) {
    obj = id;
    document.getElementById('creaModal').style.display = 'block';
    document.getElementById('feedback').innerText = feedback;
    createBtn();
}

function createBtn(){
    if (obj != null)
    {
        document.getElementById("btnDiv").innerHTML =
            '<button type="submit" class="btn btn-secondary" class="button">Salva</button>' +
            '<input type="hidden" name="id" id="id" value="' + obj + '" required/>' +
            '<button type="button" class="btn btn-verde" onclick="hideModal()" class="button">Annulla</button>';
        obj = null;
    }
}

function hideModal()
{
    document.getElementById('creaModal').style.display = 'none';
}