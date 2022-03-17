var base_url = "http://localhost:8080/";

function login() {
    var username = document.getElementById("usr").value;
    var password = document.getElementById("pwd").value;

    if(username == "") {
        window.alert("Inserisci username");
        return;
    }
    if(password == "") {
        window.alert("Inserisci password");
        return;
    }
    
    var userXmlHttp = new XMLHttpRequest();
    userXmlHttp.onreadystatechange = function() {
        if(userXmlHttp.readyState == 4) {
            if(userXmlHttp.status == 200) {
                userObj = JSON.parse(userXmlHttp.responseText);
                if(userObj.password != password) {
                    window.alert("Password errata");
                    return;
                }
                if(document.getElementById("memLogin").checked == true) { // salvataggio credenziali per mantenere l'accesso
                    userLoginSesson = window.localStorage;
                    userLoginSesson.setItem("username", username);
                    userLoginSesson.setItem("password", password);
                }
                window.location = "homePage.html";
            }
            else {
                window.alert("Utente inesistente");
                return;
            }
        }
    };
    userXmlHttp.open("GET", base_url + "artform/utente/" + username);
    userXmlHttp.send(null);
}

document.getElementById("inputUsername").addEventListener("keyup", function(event) {
    event.preventDefault();
    if (event.keyCode === 13) {
      document.getElementById("buttonLogin").click();
    }
});