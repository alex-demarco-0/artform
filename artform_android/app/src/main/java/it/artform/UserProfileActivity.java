package it.artform;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends Activity {
    ImageView userProfilePic;
    TextView usernameUserProfile;
    TextView tagsUserProfile;
    Button settings;
    Button badgeUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userProfilePic=findViewById(R.id.userProfileImageView);
        usernameUserProfile=findViewById(R.id.usernameUserProfile);
        tagsUserProfile=findViewById(R.id.tagsUserProfile);
        settings=findViewById(R.id.settingsUserProfile);
        badgeUserProfile=findViewById(R.id.badgeButtonUserProfile);

    // imposta USERNAME del profilo personale,
        AFGlobal app = (AFGlobal) getApplication();
        ArtformApiEndpointInterface apiService = app.retrofit.create(ArtformApiEndpointInterface.class);
        Call<User> getUserCall = apiService.getUserByUsername(AFGlobal.getLoggedUser());
        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UserProfileActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();

                    usernameUserProfile.setText(response.body().getName());
                    tagsUserProfile.setText(response.body().getBio());
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
                Intent intent=new Intent(UserProfileActivity.this, SettingsActivity.class);
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