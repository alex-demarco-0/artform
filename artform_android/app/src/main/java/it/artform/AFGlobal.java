package it.artform;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AFGlobal extends Application {
    public static final String BASE_URL = "http://172.29.224.1:8080/"; //varia in base alla macchina
    Retrofit retrofit = null;

    public AFGlobal() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
