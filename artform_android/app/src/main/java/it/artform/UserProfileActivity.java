package it.artform;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.artform.feed.PostGridAdapter;
import it.artform.pojos.Post;
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends Activity {
    ImageView userProfilePicImageView = null;
    TextView usernameUserProfile = null;
    TextView tagsUserProfile = null;
    Button settings = null;
    Button badgeUserProfile = null;
    GridView userPostsGridView = null;
    Post[] userPosts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userProfilePicImageView = findViewById(R.id.userProfilePicImageView);
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
                    Picasso.get().load(profilePicUri).resize(130, 130).centerCrop().into(UserProfileActivity.this.userProfilePicImageView);

                    usernameUserProfile.setText(loggedUser.getUsername());
                    tagsUserProfile.setText(loggedUser.getBio());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Richiesta GET dell'Utente non effettuata", Toast.LENGTH_LONG).show();
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

        //GET dei Post dell'utente
        Call<List<Post>> getUserPostsCall = apiService.getUserPosts(AFGlobal.getLoggedUser());
        getUserPostsCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    userPosts = new Post[response.body().size()];
                    for(int i=0; i<userPosts.length; i++)
                        userPosts[i] = response.body().get(i);
                    //Caricamento dei post dll'utente nella GridView
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

    private void openPostDetails(int pos) {
        Intent postListIntent = new Intent(UserProfileActivity.this, PostListActivity.class);
        postListIntent.putExtra("postList", userPosts);
        postListIntent.putExtra("postIndex", pos);
        startActivity(postListIntent);
    }
}