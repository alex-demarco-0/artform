var loadFile = function(event) {
    var output = document.getElementById('img');
    output.src = URL.createObjectURL(event.target.files[0]);
    output.onload = function() {
      URL.revokeObjectURL(output.src);
    }
  };

  function pubblica(){
    var titolo=document.getElementById("inputTitle").value;
    var post=document.getElementById("inputFile").value;

    if(titolo==""){
      window.alert("Inserisci un titolo");
    } else if (post==""){
      window.alert("Seleziona un file")
    }else window.alert("Pubblicazione avvenuta con successo!")
  }