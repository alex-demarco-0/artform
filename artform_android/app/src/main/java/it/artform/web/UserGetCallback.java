/*


    DEPRECATO


 */
package it.artform.web;

import android.content.Context;
import android.widget.Toast;

import it.artform.pojos.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGetCallback implements Callback<User> {
    private User user = null;
    Context ctx = null;

    public UserGetCallback(Context c) {
        super();
        ctx = c;
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.isSuccessful()) {
            user = new User(response.body().getName(), response.body().getSurname(), response.body().getUsername(),
                    response.body().getEmail(), response.body().getPhone(), response.body().getPassword(), response.body().getPoints());
            Toast.makeText(ctx, user.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }

    public User getUser() {
        return user;
    }
}
