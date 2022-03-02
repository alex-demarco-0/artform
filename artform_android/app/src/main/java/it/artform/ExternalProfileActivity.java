package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import it.artform.pojos.Badge;
import it.artform.pojos.Post;
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExternalProfileActivity extends Activity {
    ImageView externalProfilePicImageView = null;
    TextView externalProfileUsernameTextView = null;
    TextView externalUserBioTagsTextView = null;
    Button nofifyMeButton = null;
    Button externalProfileBadgeButton = null;
    Button contactMeButton = null;
    GridView userPostsGridView = null;
    RecyclerView badgesReciclerView = null;
    ArtformApiEndpointInterface apiService = null;
    Badge[] userBadges = null;
    Post[] userPosts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_profile);

        externalProfilePicImageView = findViewById(R.id.externalProfilePicImageView);
        externalProfileUsernameTextView = findViewById(R.id.externalProfileUsernameTextView);
        externalUserBioTagsTextView = findViewById(R.id.externalUserBioTagsTextView);
        nofifyMeButton = findViewById(R.id.nofifyMeButton);
        externalProfileBadgeButton = findViewById(R.id.externalProfileBadgeButton);
        contactMeButton = findViewById(R.id.contactMeButton);
        userPostsGridView = findViewById(R.id.userPostsGridView);

        AFGlobal app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        Bundle userParams = getIntent().getExtras();
        if(userParams != null) {
            String username = getIntent().getExtras().getString("username");
            if (username != null) {
                // carica e imposta i dati del profilo esterno nelle varie View
                Call<User> getUserCall = apiService.getUserByUsername(username);
                getUserCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            //loadUserData();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(ExternalProfileActivity.this, "Error while fetching user data: " + t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
                Toast.makeText(this, "Error while fetching user data", Toast.LENGTH_LONG).show();
        }

        contactMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commissionIntent = new Intent(ExternalProfileActivity.this, CommissionActivity.class);
                startActivity(commissionIntent);
            }
        });

        nofifyMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(ExternalProfileActivity.this, "Hai attivato le notifiche per l'utente *nome utente* ", Toast.LENGTH_LONG).show();
            }
        });
    }

}