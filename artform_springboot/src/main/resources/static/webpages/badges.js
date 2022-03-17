var base_url = "http://localhost:8080/";

function getBadges() {
    var badgesXmlHttp = new XMLHttpRequest();
    badgesXmlHttp.onreadystatechange = function() {
        if(badgesXmlHttp.readyState == 4)
            if(badgesXmlHttp.status == 200) {
                var badgeListObj = JSON.parse(badgesXmlHttp.responseText);
                for(var idx=0; idx<badgeListObj.length; idx++) {
                    var badge = badgeListObj[idx];
                    document.getElementById("badgesDiv").innerHTML +=
                    '<img src="' + base_url + 'media/badgesBmp/' + badge.nome + '.png">'
                    //nome badge
                    //descrizione badge
                }
            }
    };
    badgesXmlHttp.open("GET", base_url + "artform/utente/" + userObj.username + "/posts");
    badgesXmlHttp.send(null);
}