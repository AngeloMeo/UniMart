function orderSearchResult() {


    var sel = document.getElementById("order");
    let orderby = sel.options[sel.selectedIndex].value;

    switch(orderby){
        case "Nome:Crescente":
            tinysort.defaults.order='asc';
            tinysort(".prodotto", "#nome");
            break;
        case "Nome:Decrescente":
            tinysort.defaults.order='desc';
            tinysort(".prodotto", "#nome");
            break;
        case "IAN:Crescente":
            tinysort.defaults.order='asc';
            tinysort(".prodotto", "#IAN");
            break;
        case "IAN:Decrescente":
            tinysort.defaults.order='desc';
            tinysort(".prodotto", "#IAN");
            break;
        case "Prezzo:Crescente":
            tinysort.defaults.order='asc';
            tinysort(".prodotto", "#prezzo");
            break;
        case "Prezzo:Decrescente":
            tinysort.defaults.order='desc';
            tinysort(".prodotto", "#prezzo");
            break;
    }

}