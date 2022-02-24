package it.artform;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AFGlobal extends Application {
    public static final String BASE_URL = "http://172.23.224.1:8080/"; //varia in base alla macchina e alla rete
    public static final String USER_PROPIC_PATH = BASE_URL + "media/userProfilePics/";
    public static final String POST_IMAGE_PATH = BASE_URL + "media/imagePosts/";
    public static final String POST_VIDEO_PATH = BASE_URL + "media/videoPosts/";
    private static String loggedUser = null; //variabile username utente loggato
    Retrofit retrofit = null;

    public AFGlobal() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static String getLoggedUser() {
        return loggedUser;
    }

    public static String setLoggedUser(String username) {
        loggedUser = username;
        return loggedUser;
    }

}
