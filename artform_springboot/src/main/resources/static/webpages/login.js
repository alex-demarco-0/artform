function login() {
    var password=document.getElementById("pwd").value;
    var username=document.getElementById("inputUsername").value;

    if (password=="admin" && username=="admin") {
        window.location="homePage.html";
    }
    else
        window.alert("Credenziali non valide/utente inesistente");
}
document.getElementById("inputUsername").addEventListener("keyup", function(event) {
    event.preventDefault();
    if (event.keyCode === 13) {
      document.getElementById("buttonLogin").click();
    }
});