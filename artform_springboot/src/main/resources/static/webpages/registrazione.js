var base_url = "http://localhost:8080/";

function registrazione() {
  var nome = document.getElementById("nome").value;
  var cognome = document.getElementById("cognome").value;
  var email = document.getElementById("email").value;
  var username = document.getElementById("username").value;
  var telefono = document.getElementById("numeroTelefono").value;
  var password = document.getElementById("pwd").value;
  var rpassword = document.getElementById("rpwd").value;
  var registrati = document.getElementById("inputBtn").value;

  if(nome == "" || cognome == "" || email == "" || username == "" || password == "" || rpassword == ""){
    window.alert("Uno o più campi non sono validi");
    return;
  }

  userJsonData = '{"nome":"' + nome + 
                '","cognome":"' + cognome +
                '","username":"' + username + 
                '","email":"' + email + 
                '","numeroTelefono":"' + telefono + 
                '","password":"' + password + 
                '","bio":"","punteggio":"0"}';

  var registerXmlHttp = new XMLHttpRequest();
  registerXmlHttp.onreadystatechange = function() {
        if(registerXmlHttp.readyState == 4) {
            if(registerXmlHttp.status == 201) {
                window.alert("Registrazione effettuata con successo!");  // ovviamente la registrazione va completata passando dalla scelta dei Topic
                window.location = "homePage.html";
            }
            else
                window.alert("Si è verificato un problema durante la registrazione");
        }
    };
    registerXmlHttp.open("POST", base_url + "artform/utente/");
    registerXmlHttp.setRequestHeader("Content-Type", "application/json");
    registerXmlHttp.send(userJsonData);
}