package it.artform.databases;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AFRetrofit extends Application {
    public static final String BASE_URL = "http://10.0.2.2:8080/artform/utente";
    Retrofit retrofit = null;

    public AFRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
