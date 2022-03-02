/* 
 Descrizione: javascript del html login
 Data : 09/02/2022
 Data ultima modifica: 09/02/2022
 */

function registrazione() {
    window.location = 'registrazione.html';
}

function login() {
    var password = document.getElementById("password").value;
    var username = document.getElementById("username").value;
    if (password == "admin" && username == "admin") {
        window.location = "homePage.html";
    } else
        window.alert("Credenziali non valide/utente inesistente");
    document.getElementById("inputUsername").addEventListener("keyup", function (event) {
        event.preventDefault();
        if (event.keyCode === 13) {
            document.getElementById("buttonLogin").click();
        }
    });
}