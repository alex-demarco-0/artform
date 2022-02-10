package it.artform.web;

import it.artform.pojos.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGetCallback implements Callback<User> {
    private User user = null;

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() == 200)
            user = response.body();
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }

    public User getUser() {
        return user;
    }
}
