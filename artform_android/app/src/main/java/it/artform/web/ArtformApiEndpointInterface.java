package it.artform.web;

import it.artform.pojos.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ArtformApiEndpointInterface {

    @GET("http://localhost:8080/artform/utente/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    @POST("http://localhost:8080/artform/utente")
    Call<User> addUser(@Body User user);

    @PUT("http://localhost:8080/artform/utente/{username}")
    Call<User> updateUser(@Path("username") String username, @Body User user);

    @DELETE("http://localhost:8080/artform/utente/{username}")
    String deleteUser(@Path("username") String username);

}