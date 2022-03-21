package it.artform.activities.profile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.util.List;

import it.artform.AFGlobal;
import it.artform.activities.notifications.NotificationsActivity;
import it.artform.activities.search.ImagePostSearchActivity;
import it.artform.activities.settings.SettingsActivity;
import it.artform.activities.publication.ContentPubActivity;
import it.artform.activities.homepage.MainActivity;
import it.artform.activities.post.PostListActivity;
import it.artform.R;
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
    ImageButton userProfileSettingsButton = null;
    GridView userPostsGridView = null;
    RecyclerView badgesReciclerView = null;
    TextView myPointsTextView = null;
    TextView noPostsTextView = null;
    BottomNavigationView bottomNavigationView = null;

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
        userPostsGridView = findViewById(R.id.userPostsGridView);
        badgesReciclerView = findViewById(R.id.badgesReciclerView);
        LinearLayoutManager badgesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        badgesReciclerView.setLayoutManager(badgesLayoutManager);
        myPointsTextView = findViewById(R.id.myPointsTextView);
        noPostsTextView = findViewById(R.id.noPostsTextView);
        noPostsTextView.setVisibility(View.INVISIBLE);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);

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
                else
                    Toast.makeText(UserProfileActivity.this, "Error while fetching user data: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(UserProfileActivity.this, "Error while fetching user data: " + t.toString(), Toast.LENGTH_LONG).show();
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

        noPostsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent publishIntent = new Intent(UserProfileActivity.this, ContentPubActivity.class);
                startActivity(publishIntent);
            }
        });
    }

    // listener NavigationBar
    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.home_item:
                    Intent homeIntent = new Intent(UserProfileActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    break;
                case R.id.search_item:
                    Intent searchIntent = new Intent(UserProfileActivity.this, ImagePostSearchActivity.class);
                    startActivity(searchIntent);
                    break;
                case R.id.add_item:
                    Intent publishIntent = new Intent(UserProfileActivity.this, ContentPubActivity.class);
                    startActivity(publishIntent);
                    break;
                case R.id.notifications_item:
                    Intent notificationsIntent = new Intent(UserProfileActivity.this, NotificationsActivity.class);
                    startActivity(notificationsIntent);
                    break;
                case R.id.profile_item:
                    break;
            }
            return false;
        }
    };
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
                if(response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        userBadges = new Badge[response.body().size()];
                        for (int i = 0; i < userBadges.length; i++)
                            userBadges[i] = response.body().get(i);
                        RecyclerView.Adapter badgesAdapter = new BadgeListAdapter(UserProfileActivity.this, userBadges);
                        badgesReciclerView.setAdapter(badgesAdapter);
                    }
                }
                else
                    Toast.makeText(UserProfileActivity.this, "Error while fetching user Badges: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(UserProfileActivity.this, "Error while fetching user Badges: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserPosts() {
        Call<List<Post>> getUserPostsCall = apiService.getUserPosts(AFGlobal.getLoggedUser());
        getUserPostsCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        userPosts = new Post[response.body().size()];
                        for (int i = 0; i < userPosts.length; i++)
                            userPosts[i] = response.body().get(i);
                        //Caricamento dei post dell'utente nella GridView
                        userPostsGridView.setAdapter(new PostGridAdapter(UserProfileActivity.this, userPosts));
                        userPostsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                openPostDetails(position);
                            }
                        });
                    }
                    else {
                        userPostsGridView.setVisibility(View.INVISIBLE);
                        noPostsTextView.setVisibility(View.VISIBLE);
                    }
                }
                else
                    Toast.makeText(UserProfileActivity.this, "Error while fetching user Posts: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(UserProfileActivity.this, "Error while fetching user Posts: " + t.toString(), Toast.LENGTH_LONG).show();
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