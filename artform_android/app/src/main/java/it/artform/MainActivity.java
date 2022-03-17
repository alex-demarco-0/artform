package it.artform;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Date;

import it.artform.databases.PostDBAdapter;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity {
    //PostDBAdapter pdba = null;
    //Button externalProfileButton = null;
    ListView datiRegistrazioneListView = null;
    //Button notification = null;
    //Button addPost = null;
    //Button search = null;
    BottomNavigationView bottomNavigationView = null;
    MenuItem notificationsItem = null;

    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;
    Date lastReadNotifications = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TEST - dati registrazione
        datiRegistrazioneListView = findViewById(R.id.datiRegistrazioneListView);
        Bundle datiRegistrazione = getIntent().getExtras();
        if(datiRegistrazione != null) {
            String[] listaDatiRegistrazione = new String[datiRegistrazione.size()];
            int i = 0;
            for (String key : datiRegistrazione.keySet()) {
                if (datiRegistrazione.get(key) != null)
                    listaDatiRegistrazione[i] = datiRegistrazione.get(key).toString();
                i++;
            }
            ArrayAdapter datiRegistrazioneAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDatiRegistrazione);
            datiRegistrazioneListView.setAdapter(datiRegistrazioneAdapter);
        }
        //

        // TEST ADM- pulsante home
        /*Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AFGlobal app = (AFGlobal) getApplication();
                ArtformApiEndpointInterface apiService = app.retrofit.create(ArtformApiEndpointInterface.class);
                Call<User> getUserCall = apiService.getUserByUsername(AFGlobal.getLoggedUser());
                getUserCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful())
                            Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Richiesta GET non effettuata", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });*/

        // TEST - pulsante commission / Progressbar
       /* externalProfileButton = findViewById(R.id.externalProfileButton);
        externalProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openExternalProfileActivity = new Intent(MainActivity.this, ExternalProfileActivity.class);

                startActivity(openExternalProfileActivity);
            }
        });
        // TEST - pulsante profilo personale
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openUserProfileActivity = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(openUserProfileActivity);
            }
        });*/

        // TEST - custom ListView post feed
        //ArrayAdapter
        ListView feedListView = findViewById(R.id.feedListView);
        /*
        Post[] testPosts = new Post[20];
        for(int i=0; i<testPosts.length; i++)
            testPosts[i] = new Post("@mipmap/ic_launcher_foreground", "User " + (i+1), "Title #" + (i+1), "Topic", "#Tags of post " + (i+1), new Date(), 0, true);
        PostArrayAdapter paa = new PostArrayAdapter(this, R.layout.row_main, testPosts);
        feedListView.setAdapter(paa);
        */
        //CursorAdapter
        // TEST
/*
        pdba = new PostDBAdapter(this);
        try {
            pdba.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        /*
        for(int i=0; i<10; i++)
            pdba.createPost((i+1), "Title #" + (i+1), "Topic", "#Tags of post " + (i+1), new Date(), 0, true);

        Cursor allPosts = pdba.fetchAllPosts();
        PostCursorAdapter pca = new PostCursorAdapter(this, allPosts, 1);
        feedListView.setAdapter(pca);
*/
       /* addPost=(Button) findViewById(R.id.addPostButton);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ContentPubActivity.class);
                startActivity(intent);
            }
        });

        notification=(Button) findViewById(R.id.notificationButton);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        search=(Button) findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });*/
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
        //notificationsItem = findViewById(R.id.notifications_item);

        lastReadNotifications = new Date();
    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch(item.getItemId()) {
                case R.id.home_item:
                    /*selectedFragment=new HomeFragment();*/
                    break;
                case R.id.search_item:
                    Intent searchIntent = new Intent(MainActivity.this, UserSearchActivity.class);
                    startActivity(searchIntent);
                    /*selectedFragment=new SearchFragment();*/
                    break;
                case R.id.add_item:
                    Intent publishIntent = new Intent(MainActivity.this, ContentPubActivity.class);
                    startActivity(publishIntent);
                    /*selectedFragment=new AddFragment();*/
                    break;
                case R.id.notifications_item:
                    Intent notificationsIntent = new Intent(MainActivity.this, NotificationActivity.class);
                    lastReadNotifications = new Date();
                    startActivity(notificationsIntent);
                    /*selectedFragment=new NotificationsFragment();*/
                    break;
                case R.id.profile_item:
                    Intent userProfileIntent = new Intent(MainActivity.this, UserProfileActivity.class);
                    startActivity(userProfileIntent);
                    /*selectedFragment=new ProfileFragment();*/
                    break;
            }
            /*getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();*/
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        checkNotifications();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //pdba.close();
    }

    // controllo notifiche non lette
    private void checkNotifications() {
        // TEST
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);
        Call<Integer> checkUnreadNotificationsCall = apiService.checkUnreadNotifications(AFGlobal.getLoggedUser(), lastReadNotifications);
        checkUnreadNotificationsCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    //notificationsItem.set     impostare contatore badge nuove notifiche
                    // TEST
                    //Toast.makeText(MainActivity.this, "unread notifications: " + response.body(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "EXC " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
