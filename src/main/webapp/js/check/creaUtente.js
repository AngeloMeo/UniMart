function checkUsername(){
    let str = document.getElementById("username").value;
    if(str.indexOf("@", 0) != -1){
        alert("Rimuovi la @");
        return false;
    }
    else
        document.reg.submit();
}