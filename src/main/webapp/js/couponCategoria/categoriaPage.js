
function modifyForUpdateCategoria(nome, aliquota) {
    name = nome;
    document.getElementById('name').value = nome;
    document.getElementById('aliquota').value = aliquota;
    document.getElementById('creaModal').style.display = 'block';
    document.getElementById('btn1').style.display = 'none';
    createBtn();
}

function createBtn(){
    if (name != "")
    {
        document.getElementById("btnDiv").innerHTML =
            '<button type="submit" class="button" formaction="CategoriaManager/updateCategoria">Salva Modifiche</button>' +
            '<button type="submit" class="button" formaction="CategoriaManager/deleteCategoria">Elimina Categoria</button>';
        document.getElementById("name").value = name;
        name = "";
    }
    else
        document.getElementById("btnDiv").innerHTML = '<button type="submit" id="btn2" formaction="CategoriaManager/creaCategoria">Crea Categoria</button>';
}