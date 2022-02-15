/*


    DEPRECATO


 */
package it.artform.web;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

import it.artform.pojos.User;
import retrofit2.Call;

public class GetLoggingUserTask extends AsyncTask<String, Void, User> {
    ArtformApiEndpointInterface apiService = null;
    ProgressBar loggingProgressBar = null;
    User loggingUser = null;

    public GetLoggingUserTask(ArtformApiEndpointInterface apiService, ProgressBar pb, User u) {
        this.apiService = apiService;
        loggingProgressBar = pb;
        loggingUser = u;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        loggingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected User doInBackground(String... username) {
        User pendingUser = null;
        Call<User> getLoggingUser = apiService.getUserByUsername(username[0]);
        try {
            pendingUser = getLoggingUser.execute().body();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return pendingUser;
    }

    @Override
    protected void onPostExecute(User pendingUser) {
        //super.onPostExecute(loggingUser);
        this.loggingUser = pendingUser;
        loggingProgressBar.setVisibility(View.INVISIBLE);
    }
}