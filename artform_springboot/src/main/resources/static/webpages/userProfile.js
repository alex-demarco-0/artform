var base_url = "http://localhost:8080/";
var userObj = null;

function getUser() {
    var userXmlHttp = new XMLHttpRequest();
    userXmlHttp.onreadystatechange = function() {
        if(userXmlHttp.readyState == 4 && userXmlHttp.status == 200) {
            userObj = JSON.parse(userXmlHttp.responseText);
            document.getElementById("propic").setAttribute("src", base_url + "media/userProfilePics/" + userObj.username + ".jpg");
            document.getElementById("userUsername").innerHTML = userObj.username;
            document.getElementById("userBio").innerHTML = userObj.bio;
            getUserPosts();
        }
    };
    var userLoginSesson = window.localStorage;
    var userUri = base_url + "artform/utente/" + userLoginSesson.getItem("username"); // utente loggato
    userXmlHttp.open("GET", userUri);
    userXmlHttp.send(null);
}

function getUserPosts() {
    var postsXmlHttp = new XMLHttpRequest();
    postsXmlHttp.onreadystatechange = function() {
        if(postsXmlHttp.readyState == 4 && postsXmlHttp.status == 200) {
            var postListObj = JSON.parse(postsXmlHttp.responseText);
            for(var idx=0; idx<postListObj.length; idx++) {
                var post = postListObj[idx];
                document.getElementById("posts").innerHTML +=
                '<img src="' + base_url + 'media/imagePosts/' + post.id + '.jpg">'
            }
        }
    };
    postsXmlHttp.open("GET", base_url + "artform/utente/" + userObj.username + "/posts");
    postsXmlHttp.send(null);
}

function goToSettings() {
    window.location="settings.html";
}