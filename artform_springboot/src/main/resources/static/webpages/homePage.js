var base_url = "http://localhost:8080/";
var username = window.localStorage.getItem("username");

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

function login() {
    window.location ="loginhtml.html";
}

function userProfile(){
    window.location="userProfile.html";
}
function notifiche(){
    window.location="notifiche.html";
}

function homepage(){
    window.location="homePage.html";
}

function search(){
  window.location="ricerca.html";
}