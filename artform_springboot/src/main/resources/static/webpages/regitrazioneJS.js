function onPageLoaded(){
    
}

function doSumbit(){
    var formData = new FormData(document.forms.formRegistrazione);
    var jsonData = JSON.stringify(Object.fromEntries(formData));
    
    var uriAddr = ".artform/utente";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState == 4){
            if(xmlHttp.status == 201)
                console.log("UTENTE CREATO");
        }
    }
    xmlHttp.open("POST", uriAddr);
    xmlHttp.setRequestHeader("Content-type", "application/json");
    return false;
}