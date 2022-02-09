package it.artform.web;

import it.artform.pojos.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailCheckCallback implements Callback<User> {
    private String resString = null;

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() == 200)
            resString = "E-mail già esistente";
        else if(response.code() != 404)
            resString = "ERROR CHECKING EMAIL";
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        resString = "ERROR CHECKING EMAIL";
    }

    public String toString() {
        return resString;
    }
}
