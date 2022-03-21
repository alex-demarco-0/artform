var base_url = "http://localhost:8080/";
var formData = null;

function pubblicaCommissione() {
    var titolo = document.getElementById("inputTitle").value;
    var offerta = document.getElementById("money").value;
    var topic= document.getElementsById("topicSelect").value;
    var dataScadenza= document.getElementById("date").value;
  
    if(titolo == "") {
      window.alert("Inserisci un titolo");
      return;
    }
    else if (offerta == "") {
      window.alert("Seleziona un' offerta");
      return;
    }
    else if(topic==""){
        window.alert("Inserisci un Topic");
    }
    else if(dataScadenza==""){
        window.alert("Inserisci una data di scadenza");
    }
  
    var artist = window.localStorage.getItem("username");
    var commissioniJsonData = '{"artistaUsername":"' + artist + 
                  '","titolo":"' + titolo +
                  '","offerta":"' + offerta +
                  '","topic":"' + topic + 
                  '","data scadenza":"' + dataScadenza +
                  '"}';
  
    var addCommissioneXmlHttp = new XMLHttpRequest();
    addPostXmlHttp.onreadystatechange = function() {
          if(addCommissioneXmlHttp.readyState == 4) {
              if(addCommissioneXmlHttp.status == 201) {
                  window.alert("Pubblicazione avvenuta con successo!");
              }
              else
                  window.alert("Si è verificato un problema durante la pubblicazione della commissione");
          }
      };
      addCommissioneXmlHttp.open("POST", base_url + "artform/commisioni");
      addCommisioneXmlHttp.setRequestHeader("Content-Type", "application/json");
      addCommissioneXmlHttp.send(commissioniJsonData);
  }