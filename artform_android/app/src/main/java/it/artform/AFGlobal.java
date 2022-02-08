package it.artform;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AFGlobal extends Application {
    public static final String BASE_URL = "http://10.0.2.2:8080";
    Retrofit retrofit = null;

    public AFGlobal() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
