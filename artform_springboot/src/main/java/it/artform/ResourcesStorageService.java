package it.artform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.artform.pojos.Post;
import it.artform.pojos.Utente;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/*
 * Gestore delle risorse (Post (immagini e video), immagini profilo degli Utenti) nello storage
 */

@Service
public class ResourcesStorageService {
    private final Path userProfilePicsStorageLocation;
    private final Path imagePostsStorageLocation;
    private final Path videoPostsStorageLocation;

    @Autowired
    public ResourcesStorageService() {
        this.userProfilePicsStorageLocation = Paths.get("src/main/resources/static/media/userProfilePics/");
        this.imagePostsStorageLocation = Paths.get("src/main/resources/static/media/imagePosts/");
        this.videoPostsStorageLocation = Paths.get("src/main/resources/static/media/videoPosts/");
    }

    public void storeProfilePic(MultipartFile resource, Utente user) {
    	String fileExtension = ".jpg";
        String fileName = user.getUsername() + fileExtension;
        Path targetLocation = this.userProfilePicsStorageLocation.resolve(fileName);
        try {
            Files.copy(resource.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void storeImagePost(MultipartFile resource, Post post) {
    	String fileExtension = ".jpg";
        String fileName = post.getId() + fileExtension;
        Path targetLocation = this.imagePostsStorageLocation.resolve(fileName);
        try {
            Files.copy(resource.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void storeVideoPost(MultipartFile resource, Post post) {
    	String fileExtension = ".mp4";
        String fileName = post.getId() + fileExtension;
        Path targetLocation = this.videoPostsStorageLocation.resolve(fileName);
        try {
            Files.copy(resource.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }  

}