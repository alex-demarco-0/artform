var base_url = "http://localhost:8080/";

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

function searchImages() {
    var imagePostsXmlHttp = new XMLHttpRequest();
    imagePostsXmlHttp.onreadystatechange = function() {
        if(imagePostsXmlHttp.readyState == 4)
            if(imagePostsXmlHttp.status == 200) {
                var postListObj = JSON.parse(imagePostsXmlHttp.responseText);
                for(var idx=0; idx<postListObj.length; idx++) {
                    var post = postListObj[idx];
                    document.getElementById("searchResults").innerHTML +=
                    '<img src="' + base_url + 'media/imagePosts/' + post.id + '.jpg">'
                }
            }
    };
    var keywords = document.getElementById("inputSearch").value;
    imagePostsXmlHttp.open("GET", base_url + 'artform/post/topic=""/keywords="' + keywords + '"/type="img"'); //manca da implementare il filtro per i Topics
    imagePostsXmlHttp.send(null);
}

function searchVieos() {
    
}

function searchUsers() {
    
}