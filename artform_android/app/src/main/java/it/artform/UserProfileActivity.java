package it.artform;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.squareup.picasso.Picasso;

import java.util.List;

import it.artform.feed.BadgeListAdapter;
import it.artform.feed.PostGridAdapter;
import it.artform.pojos.Badge;
import it.artform.pojos.Post;
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends Activity {
    ImageView userProfilePicImageView = null;
    TextView userProfileUsernameTextView = null;
    TextView userBioTagsTextView = null;
    Button userProfileSettingsButton = null;
    //Button userProfileBadgeButton = null;
    GridView userPostsGridView = null;
    RecyclerView badgesReciclerView = null;
    TextView myPointsTextView = null;
    ArtformApiEndpointInterface apiService = null;
    User loggedUser = null;
    Badge[] userBadges = null;
    Post[] userPosts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userProfilePicImageView = findViewById(R.id.userProfilePicImageView);
        userProfileUsernameTextView = findViewById(R.id.userProfileUsernameTextView);
        userBioTagsTextView = findViewById(R.id.userBioTagsTextView);
        userProfileSettingsButton = findViewById(R.id.userProfileSettingsButton);
        //userProfileBadgeButton = findViewById(R.id.userProfileBadgeButton);
        userPostsGridView = findViewById(R.id.userPostsGridView);
        badgesReciclerView = findViewById(R.id.badgesReciclerView);
        LinearLayoutManager badgesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        badgesReciclerView.setLayoutManager(badgesLayoutManager);
        myPointsTextView = findViewById(R.id.myPointsTextView);

        AFGlobal app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        // carica e imposta i dati del profilo personale nelle varie View
        Call<User> getUserCall = apiService.getUserByUsername(AFGlobal.getLoggedUser());
        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    loggedUser = response.body();
                    loadUserData();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Richiesta GET dell'Utente non effettuata", Toast.LENGTH_LONG).show();
            }
        });
/*
        userProfileBadgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
        userProfileSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openSettingsActivity= new Intent(UserProfileActivity.this, SettingsActivity.class);
                startActivity(openSettingsActivity);
            }
        });
    }
/*
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
        }
        return false;
    }
*/
    private void loadUserData() {
        // immagine del profilo
        String profilePicUri = AFGlobal.USER_PROPIC_PATH + AFGlobal.getLoggedUser() + ".jpg";
        Picasso.get().load(profilePicUri).resize(130, 130).centerCrop().into(UserProfileActivity.this.userProfilePicImageView);
        // username
        userProfileUsernameTextView.setText(loggedUser.getUsername());
        // bio
        userBioTagsTextView.setText(loggedUser.getBio());
        // lista dei badge
        loadUserBadges();
        // contatore punteggio
        myPointsTextView.append(String.valueOf(loggedUser.getPoints()));
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
                    RecyclerView.Adapter badgesAdapter = new BadgeListAdapter(UserProfileActivity.this, userBadges);
                    badgesReciclerView.setAdapter(badgesAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Richiesta GET dei Badge dell'Utente non effettuata", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserPosts() {
        Call<List<Post>> getUserPostsCall = apiService.getUserPosts(AFGlobal.getLoggedUser());
        getUserPostsCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    userPosts = new Post[response.body().size()];
                    for(int i=0; i<userPosts.length; i++)
                        userPosts[i] = response.body().get(i);
                    //Caricamento dei post dell'utente nella GridView
                    if(userPosts.length > 0) {
                        userPostsGridView.setAdapter(new PostGridAdapter(UserProfileActivity.this, userPosts));
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
                Toast.makeText(UserProfileActivity.this, "Richiesta GET dei Post dell'Utente non effettuata", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openPostDetails(int pos) {
        Intent postListIntent = new Intent(UserProfileActivity.this, PostListActivity.class);
        postListIntent.putExtra("postList", userPosts);
        postListIntent.putExtra("postIndex", pos);
        startActivity(postListIntent);
    }
}