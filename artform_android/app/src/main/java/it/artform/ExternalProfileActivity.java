package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.artform.feed.PostGridAdapter;
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
    //Button externalProfileBadgeButton = null;
    Button contactMeButton = null;
    GridView userPostsGridView = null;
    RecyclerView badgesReciclerView = null;
    ArtformApiEndpointInterface apiService = null;
    User user = null;
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
        //externalProfileBadgeButton = findViewById(R.id.externalProfileBadgeButton);
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
                            user = response.body();
                            loadUserData();
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
                //if not active
                Toast.makeText(ExternalProfileActivity.this, "Notifications for user " + user.getUsername() + " activated", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserData() {
        // immagine del profilo
        String profilePicUri = AFGlobal.USER_PROPIC_PATH + user.getUsername() + ".jpg";
        Picasso.get().load(profilePicUri).resize(130, 130).centerCrop().into(ExternalProfileActivity.this.externalProfilePicImageView);
        // username
        externalProfileUsernameTextView.setText(user.getUsername());
        // bio
        externalUserBioTagsTextView.setText(user.getBio());
        // lista dei badge


        //GET dei Post dell'utente
        loadUserPosts();
    }

    private void loadUserBadges() {
        Call<List<Badge>> getUserBadgesCall = apiService.getUserBadges(AFGlobal.getLoggedUser());
        getUserBadgesCall.enqueue(new Callback<List<Badge>>() {
            @Override
            public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                if(response.isSuccessful() && response.body().size() > 0) {
                    userBadges = new Badge[response.body().size()];
                    for(int i=0; i<userBadges.length; i++)
                        userBadges[i] = response.body().get(i);
                    //RecyclerView.Adapter badgesAdapter = new BadgesListAdapter(UserProfileActivity.this, userBadges);
                    //badgesReciclerView.setAdapter(badgesAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {
                Toast.makeText(ExternalProfileActivity.this, "Richiesta GET dei Post dell'Utente non effettuata", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserPosts() {
        Call<List<Post>> getUserPostsCall = apiService.getUserPosts(user.getUsername());
        getUserPostsCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    userPosts = new Post[response.body().size()];
                    for(int i=0; i<userPosts.length; i++)
                        userPosts[i] = response.body().get(i);
                    //Caricamento dei post dell'utente nella GridView
                    if(userPosts.length > 0) {
                        userPostsGridView.setAdapter(new PostGridAdapter(ExternalProfileActivity.this, userPosts));
                        userPostsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                openPostDetails(position);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(ExternalProfileActivity.this, "Richiesta GET dei Post dell'Utente non effettuata", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openPostDetails(int pos) {
        Intent postListIntent = new Intent(ExternalProfileActivity.this, PostListActivity.class);
        postListIntent.putExtra("postList", userPosts);
        postListIntent.putExtra("postIndex", pos);
        startActivity(postListIntent);
    }

}