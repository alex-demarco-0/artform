var base_url = "http://localhost:8080/";
var username = window.localStorage.getItem("username");

function externalProfile(){
  window.location="externalProfile.html"
}

function changeLike(){
var image= document.getElementById('likeBtn');
if(image.src.match("heart.png")){
   image.src="heartBlack.png";
}else{
  image.src="heart.png";
}
}

function changeSave(){
var image= document.getElementById('saveBtn');
if(image.src.match("diskette.png")){
   image.src="disketteBlack.png";
}else{
  image.src="diskette.png";
}
}
