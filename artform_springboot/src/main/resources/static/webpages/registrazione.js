function registrazione(){
    var nome=document.getElementById("nome").value;
    var cognome=document.getElementById("cognome").value;
    var username=document.getElementById("username").value;
    var email=document.getElementById("email").value;
    var password=document.getElementById("pwd").value;
    var telefono=document.getElementById("numeroTelefono").value;
    var registrati=document.getElementById("inputBtn").value;

    if(nome==""||cognome==""||username==""||email==""||password==""||telefono==""){
      window.alert("Uno o più campi non sono validi");
    }else window.location="tags.html";
  }