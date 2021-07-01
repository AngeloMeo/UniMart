<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" ></script>


function partialsearch(){
    $.ajax("/searchBar", {
        data: document.getElementById("myInput"),
        type: GET,
        success : populate(responseJson)
    })
}

function populate(){



}