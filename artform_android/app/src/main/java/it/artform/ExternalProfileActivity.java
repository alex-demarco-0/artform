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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.artform.feed.BadgeListAdapter;
import it.artform.feed.PostGridAdapter;
import it.artform.pojos.Badge;
import it.artform.pojos.Post;
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExternalProfileActivity extends Activity {
    ImageView externalProfilePicImageView = null;
    TextView externalProfileUsernameTextView = null;
    TextView externalUserBioTagsTextView = null;
    Button nofifyMeButton = null;
    Button contactMeButton = null;
    GridView userPostsGridView = null;
    RecyclerView badgesReciclerView = null;
    ArtformApiEndpointInterface apiService = null;
    User user = null;
    Badge[] userBadges = null;
    Post[] userPosts = null;
    boolean activeNotif = false; //flag che tiene conto se le notifiche per questo utente sono state attivate o meno

    private class disableNotifButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Call<ResponseBody> deactivateUserNotificationsCall = apiService.deactivateUserNotifications(AFGlobal.getLoggedUser(), user.getUsername());
            deactivateUserNotificationsCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        activeNotif = false;
                        Toast.makeText(ExternalProfileActivity.this, "Notifications for user " + user.getUsername() + " deactivated", Toast.LENGTH_SHORT).show();
                        nofifyMeButton.setText("Enable notifications");
                        //set onclicklistener class
                        nofifyMeButton.setOnClickListener(new enableNotifButtonClickListener());
                    }
                    else
                        Toast.makeText(ExternalProfileActivity.this, "Error while deactivating notifications for this user: ERROR " + response.code(), Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(ExternalProfileActivity.this, "Error while deactivating notifications for this user: " + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private class enableNotifButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            RequestBody username2 = RequestBody.create(MediaType.parse("text/plain"), user.getUsername());
            Call<User> activateUserNotificationsCall = apiService.activateUserNotifications(AFGlobal.getLoggedUser(), username2);
            activateUserNotificationsCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()) {
                        activeNotif = true;
                        Toast.makeText(ExternalProfileActivity.this, "Notifications for user " + user.getUsername() + " activated", Toast.LENGTH_SHORT).show();
                        nofifyMeButton.setText("Disable notifications");
                        //set onclicklistener class
                        nofifyMeButton.setOnClickListener(new disableNotifButtonClickListener());
                    }
                    else
                        Toast.makeText(ExternalProfileActivity.this, "Error while activating notifications for this user: ERROR " + response.code(), Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(ExternalProfileActivity.this, "Error while activating notifications for this user: " + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_profile);

        externalProfilePicImageView = findViewById(R.id.externalProfilePicImageView);
        externalProfileUsernameTextView = findViewById(R.id.externalProfileUsernameTextView);
        externalUserBioTagsTextView = findViewById(R.id.externalUserBioTagsTextView);
        nofifyMeButton = findViewById(R.id.nofifyMeButton);
        contactMeButton = findViewById(R.id.contactMeButton);
        userPostsGridView = findViewById(R.id.userPostsGridView);
        badgesReciclerView = findViewById(R.id.badgesReciclerView);
        LinearLayoutManager badgesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        badgesReciclerView.setLayoutManager(badgesLayoutManager);

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
                        else
                            Toast.makeText(ExternalProfileActivity.this, "Error while fetching user data: ERROR " + response.code(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(ExternalProfileActivity.this, "Error while fetching user data: " + t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
                Toast.makeText(this, "Error while fetching user data (none selected)", Toast.LENGTH_LONG).show();
        }

        contactMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commissionIntent = new Intent(ExternalProfileActivity.this, CommissionActivity.class);
                startActivity(commissionIntent);
            }
        });

        //notifyButtonSetup();
        if(activeNotif) {
            nofifyMeButton.setText("Disable notifications");
            nofifyMeButton.setOnClickListener(new disableNotifButtonClickListener());
        }
        else {
            nofifyMeButton.setText("Enable notifications");
            nofifyMeButton.setOnClickListener(new enableNotifButtonClickListener());
        }
    }

    private void loadUserData() {
        // immagine del profilo
        String profilePicUri = AFGlobal.USER_PROPIC_PATH + user.getUsername() + ".jpg";
        Picasso.get().load(profilePicUri).resize(130, 130).centerCrop().into(ExternalProfileActivity.this.externalProfilePicImageView);
        // username
        externalProfileUsernameTextView.setText(user.getUsername());
        // bio
        externalUserBioTagsTextView.setText(user.getBio());
        // pulsante notificami
        checkUserNotifications();
        // lista dei badge
        loadUserBadges();
        //GET dei Post dell'utente
        loadUserPosts();
    }

    private void checkUserNotifications() {
        Call<User> checkUserNotificationsCall = apiService.checkUserNotifications(AFGlobal.getLoggedUser(), user.getUsername());
        checkUserNotificationsCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                ExternalProfileActivity.this.activeNotif = (response.isSuccessful() ? true : false);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ExternalProfileActivity.this, "Error while checking if notifications for this user are active: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
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
                    RecyclerView.Adapter badgesAdapter = new BadgeListAdapter(ExternalProfileActivity.this, userBadges);
                    badgesReciclerView.setAdapter(badgesAdapter);
                }
                else
                    Toast.makeText(ExternalProfileActivity.this, "Error while fetching user Badges: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ExternalProfileActivity.this, "Error while fetching user Badges: " + t.toString(), Toast.LENGTH_LONG).show();
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
                else
                    Toast.makeText(ExternalProfileActivity.this, "Error while fetching user Posts: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ExternalProfileActivity.this, "Error while fetching user Posts: " + t.toString(), Toast.LENGTH_LONG).show();
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