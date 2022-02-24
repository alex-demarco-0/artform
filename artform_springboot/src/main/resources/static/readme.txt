path messo a disposizione da Spring Boot accessibile tramite http://localhost:8080/
la directory 'webpages' contiene i file .html, .js, .css costituenti il servizio web;
- ! -
i file caricati nella directory 'artform_web' dovrebbero essere trasferiti in questa directory: artform_springboot\src\main\resources\static\webpages
- ! -
la directory 'media' contiene l'immagine del logo e le directory 'imagePosts', 'videoPosts', 'userProfilePics' costituenti il deposito delle risorse immagini/video;
le directory contengono rispettivamente i post degli utenti (formato immagine), i post degli utenti (formato video), le immagini dei profili degli utenti (formato immagine).
I file dei post degli utenti (sia immagine che video) saranno nominati come {Id}.{estensione}.
Le immagini dei profili degli utenti saranno nominati come {username}.{estensione}.
Si potrebbero eliminare le colonne/attributi di Post e Utente relativi ai path delle immagini:
-memorizzando il path;
-risalendo ai nomi dei file (post: id.jpg) (immagine profilo: username.jpg)