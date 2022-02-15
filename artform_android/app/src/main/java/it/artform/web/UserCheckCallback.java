/*


    DEPRECATO


 */
package it.artform.web;

import it.artform.pojos.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserCheckCallback implements Callback<User> {
    private boolean emailCheck = true; // 1 = true = controllo email, 0 = false = controllo username
    private String resString = null;

    public UserCheckCallback(int i) {
        super();
        if(i == 0)
            emailCheck = false;
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() == 200)
            resString = (emailCheck ? "Email" : "Username") + " già esistente";
        /*
        else if(response.code() != 404)
            resString = "ERROR CHECKING " + (emailCheck ? "Email" : "Username");
        */
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        resString = "Problema durante la verifica di " + (emailCheck ? "Email" : "Username") + ": " + t.toString();
    }

    public String toString() {
        return resString;
    }
}
