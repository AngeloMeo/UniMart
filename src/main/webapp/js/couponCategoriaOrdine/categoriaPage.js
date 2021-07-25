function modifyForUpdateCategoria(nome, aliquota) {
    name = nome;
    document.getElementById('creaModal').style.display = 'block';
    document.getElementById('btn-crea').style.display = 'none';
    createBtn(aliquota);
}

function createBtn(aliquota){
    if (name != "")
    {
        document.getElementById("btnDiv").innerHTML =
            '<button type="submit" class="btn btn-verde" formaction="CategoriaManager/updateCategoria">Salva Modifiche</button>' +
            '<button type="submit" class="btn btn-primary" formaction="CategoriaManager/deleteCategoria" onclick="removeRequired()">Elimina Categoria</button>';
        document.getElementById("name").value = name;
        document.getElementById('aliquota').value = aliquota;
        $("#name").prop('required', true);
        $("#name").prop('readOnly', true);
        name = "";
    }
    else
    {
        document.getElementById('name').value = name;
        document.getElementById('aliquota').value = '';
        document.getElementById("btnDiv").innerHTML = '<button type="submit" id="btn2" formaction="CategoriaManager/creaCategoria" class="btn btn-secondary">Crea Categoria</button>';
    }
}

function removeRequired()
{
    $("#aliquota").prop('required', false);
}