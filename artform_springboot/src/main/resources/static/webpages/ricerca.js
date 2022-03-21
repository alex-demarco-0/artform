var base_url = "http://localhost:8080/";
var formData = null;
var username = window.localStorage.getItem("username");

function search() {
    if(document.getElementById("inputSearch").value == "")
        return;
    if(document.getElementById("imageCategory").checked) {
        searchImages();
        return;
    }
    if(document.getElementById("videoCategory").checked) {
        searchVieos();
        return;
    }
    if(document.getElementById("userCategory").checked) {
        searchUsers();
        return;
    }
}

function fetchFeedPosts() {
    var userTopicsXmlHttp = new XMLHttpRequest();
    userTopicsXmlHttp.onreadystatechange = function() {
        if(userTopicsXmlHttp.readyState == 4) 
            if(userTopicsXmlHttp.status == 200) {
                var topicListObj = JSON.parse(userTopicsXmlHttp.responseText);
                for(var idx=0; idx<topicListObj.length; idx++) {
                    fetchPostsByTopic(topicListObj[idx].nome);
                }
            }
    };
    var userTopicsUri = base_url + "artform/utente/" + username + "/topics";
    userTopicsXmlHttp.open("GET", userTopicsUri);
    userTopicsXmlHttp.send(null);
}


//manca la funzione per il filtro dei topics
function fetchPostsByTopic(topic) {
    var topicPostsXmlHttp = new XMLHttpRequest();
    topicPostsXmlHttp.onreadystatechange = function() {
        if(topicPostsXmlHttp.readyState == 4) 
            if(topicPostsXmlHttp.status == 200) {
                var postListObj = JSON.parse(topicPostsXmlHttp.responseText);
                for(var idx=0; idx<postListObj.length; idx++) {
                    var post = postListObj[idx];
                    document.getElementById("posts").innerHTML +=
                    '<img src="' + base_url + 'media/imagePosts/' + post.id + '.jpg" height="200px" width="350px">'
                }
            }
    };
    var topicPostsUri = base_url + "artform/post/" + username + "/" + topic;
    topicPostsXmlHttp.open("GET", topicPostsUri);
    topicPostsXmlHttp.send(null);
}

function searchImages() {
    var imagePostsXmlHttp = new XMLHttpRequest();
    imagePostsXmlHttp.onreadystatechange = function() {
        if(imagePostsXmlHttp.readyState == 4)
            if(imagePostsXmlHttp.status == 200) {
                var postListObj = JSON.parse(imagePostsXmlHttp.responseText);
                for(var idx=0; idx<postListObj.length; idx++) {
                    var post = postListObj[idx];
                    document.getElementById("searchResults").innerHTML +=
                    '<img src="' + base_url + 'media/imagePosts/' + post.id + '.jpg" height="200px" width="350px">'
                }
            }
    };
    
    function searchVieos() {
    var videoPostsXmlHttp = new XMLHttpRequest();
    videoPostsXmlHttp.onreadystatechange = function() {
        if(videoPostsXmlHttp.readyState == 4)
            if(videoPostsXmlHttp.status == 200) {
                var postListObj = JSON.parse(videoPostsXmlHttp.responseText);
                for(var idx=0; idx<postListObj.length; idx++) {
                    var post = postListObj[idx];
                    document.getElementById("searchResults").innerHTML +=
                    '<video src="'+ base_url + 'media/videoPosts/'+ post.id +'.mp4"  height="230px" width="350px">'
                }
            }
    };
    var keywords = document.getElementById("inputSearch").value;
    videoPostsXmlHttp.open("GET", base_url + 'artform/post/topic=""/keywords="' + keywords + '"/type="video"'); //manca da implementare il filtro per i Topics
    videoPostsXmlHttp.send(null);
}
    var keywords = document.getElementById("inputSearch").value;
    imagePostsXmlHttp.open("GET", base_url + 'artform/post/topic=""/keywords="' + keywords + '"/type="img"'); //manca da implementare il filtro per i Topics
    imagePostsXmlHttp.send(null);
}

function searchUsers() {
    var userXmlHttp = new XMLHttpRequest();
    userXmlHttp.onreadystatechange = function() {
        if(userXmlHttp.readyState == 4) 
            if(userXmlHttp.status == 200) {
                userObj = JSON.parse(userXmlHttp.responseText);
                document.getElementById("propic").setAttribute("src", base_url + "media/userProfilePics/" + userObj.username + ".jpg");
                document.getElementById("userUsername").innerHTML = userObj.username;
                document.getElementById("userBio").innerHTML = userObj.bio;
                getUserPosts();
            }
    };
    var userLoginSesson = window.localStorage;
    var userUri = base_url + "artform/utente/" + userLoginSesson.getItem("username"); // utente loggati
    userXmlHttp.open("GET", userUri);
    userXmlHttp.send(null);
    
}
