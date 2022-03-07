/* 
 * Data: 07/03/2022
 * Descrizione: JavaScript della pagina HTML 'registrazione'
 */


function register() {
    var password = document.getElementById("pwd").value;
    var confermaPassword = document.getElementById("Cpwd").value;
    if (confermaPassword != password) {
        window.alert("Password diversi");
    } else {
        window.location = 'homePage.html';
    }
}