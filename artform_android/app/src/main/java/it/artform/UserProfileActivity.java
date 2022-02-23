package it.artform;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import it.artform.web.DownloadImageTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends Activity {
    ImageView userProfilePic = null;
    TextView usernameUserProfile = null;
    TextView tagsUserProfile = null;
    Button settings = null;
    Button badgeUserProfile = null;
    GridView userPostsGridView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userProfilePic = findViewById(R.id.userProfilePicImageView);
        usernameUserProfile = findViewById(R.id.userProfileUsernameTextView);
        tagsUserProfile = findViewById(R.id.userBioTagsTextView);
        settings = findViewById(R.id.userProfileSettingsButton);
        badgeUserProfile = findViewById(R.id.userProfileBadgeButton);
        userPostsGridView = findViewById(R.id.userPostsGridView);

        // carica e imposta i dati del profilo personale nelle View
        AFGlobal app = (AFGlobal) getApplication();
        ArtformApiEndpointInterface apiService = app.retrofit.create(ArtformApiEndpointInterface.class);
        Call<User> getUserCall = apiService.getUserByUsername(AFGlobal.getLoggedUser());
        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User loggedUser = response.body();
                    //Toast.makeText(UserProfileActivity.this, loggedUser.toString(), Toast.LENGTH_LONG).show();
                    String profilePicUri = AFGlobal.BASE_URL + loggedUser.getProfilePicSrc();
                    //new DownloadImageTask(UserProfileActivity.this.userProfilePic).execute(profilePicUri);
                    Picasso.get().load(profilePicUri).resize(130, 130).centerCrop().into(UserProfileActivity.this.userProfilePic);

                    usernameUserProfile.setText(loggedUser.getUsername());
                    tagsUserProfile.setText(loggedUser.getBio());


                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Richiesta GET non effettuata", Toast.LENGTH_LONG).show();
            }
        });

        badgeUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openSettingsActivity= new Intent(UserProfileActivity.this, SettingsActivity.class);
                startActivity(openSettingsActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        /*
        switch (itemId) {
            case R.id.SETTINGS:
                //Intent profileSettings=new Intent(this, profileSettings.class);
                //startActivity(profileSettings);
                break;
            case R.id.SAVED:
                //Intent savedPost=new Intent(this, savedPost.class);
                //startActivity(savedPost);
                break;
        }*/
        return false;
    }
}