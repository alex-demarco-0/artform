package it.artform.activities.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import it.artform.AFGlobal;
import it.artform.activities.homepage.MainActivity;
import it.artform.activities.notifications.NotificationsActivity;
import it.artform.activities.post.PostListActivity;
import it.artform.activities.profile.UserProfileActivity;
import it.artform.activities.publication.ContentPubActivity;
import it.artform.R;
import it.artform.feed.PostGridAdapter;
import it.artform.pojos.Post;
import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagePostSearchActivity extends Activity {
    SearchView contentSearchView = null;
    Button searchArtworksButton = null;
    Button searchVideosButton = null;
    Button searchArtistsButton = null;
    Spinner topicSpinner = null;
    GridView artworksGridView = null;
    TextView noResultTextView = null;
    BottomNavigationView bottomNavigationView = null;

    ArtformApiEndpointInterface apiService = null;

    String selectedTopic = null;
    Post[] searchedPosts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_post_search);

        //widget setup
        contentSearchView = findViewById(R.id.contentSearchView); //search while typing
        contentSearchView.setQueryHint("Search…");
        searchArtworksButton = findViewById(R.id.searchArtworksButton); //no listener
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchArtworksButton.setBackgroundColor(ImagePostSearchActivity.this.getColor(R.color.purple_500));
        }
        searchVideosButton = findViewById(R.id.searchVideosButton); //activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchVideosButton.setBackgroundColor(ImagePostSearchActivity.this.getColor(R.color.white));
        }
        searchArtistsButton = findViewById(R.id.searchArtistsButton); //activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchArtistsButton.setBackgroundColor(ImagePostSearchActivity.this.getColor(R.color.white));
        }
        topicSpinner = findViewById(R.id.topicSpinner); //fetch topics
        artworksGridView = findViewById(R.id.artworksGridView); //load posts
        noResultTextView = findViewById(R.id.noResultTextView); //show only when no posts
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);

        //web services setup
        AFGlobal app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        //GET dei Topic
        fetchTopics();

        //imposta SearchView da parametri
        Bundle searchParams = getIntent().getExtras();
        if(searchParams != null)
            contentSearchView.setQuery(searchParams.getCharSequence("keywords"), false);

        //listener barra di ricerca
        contentSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                fetchPosts((selectedTopic == null ? "" : selectedTopic), String.valueOf(contentSearchView.getQuery()));
                return true;
            }
        });

        //listener pulsante Video
        searchVideosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoSearchIntent = new Intent(ImagePostSearchActivity.this, VideoPostSearchActivity.class);
                if(selectedTopic != null)
                    videoSearchIntent.putExtra("topic", selectedTopic);
                videoSearchIntent.putExtra("keywords", contentSearchView.getQuery());
                startActivity(videoSearchIntent);
            }
        });

        //listener pulsante Artisti
        searchArtistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userSearchIntent = new Intent(ImagePostSearchActivity.this, UserSearchActivity.class);
                if(selectedTopic != null)
                    userSearchIntent.putExtra("topic", selectedTopic);
                userSearchIntent.putExtra("keywords", contentSearchView.getQuery());
                startActivity(userSearchIntent);
            }
        });
    }

    // listener NavigationBar
    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.home_item:
                    Intent homeIntent = new Intent(ImagePostSearchActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    break;
                case R.id.search_item:
                    break;
                case R.id.add_item:
                    Intent publishIntent = new Intent(ImagePostSearchActivity.this, ContentPubActivity.class);
                    startActivity(publishIntent);
                    break;
                case R.id.notifications_item:
                    Intent notificationsIntent = new Intent(ImagePostSearchActivity.this, NotificationsActivity.class);
                    startActivity(notificationsIntent);
                    break;
                case R.id.profile_item:
                    Intent userProfileIntent = new Intent(ImagePostSearchActivity.this, UserProfileActivity.class);
                    startActivity(userProfileIntent);
                    break;
            }
            return false;
        }
    };

    private void fetchTopics() {
        Call<List<Topic>> getTopicsCall = apiService.getAllTopics();
        getTopicsCall.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if(response.isSuccessful()) {
                    String[] topics = new String[response.body().size() + 1];
                    topics[0] = "Select topic:";
                    for(int i=1; i<topics.length; i++)
                        topics[i] = response.body().get(i-1).getName();
                    ArrayAdapter topicsAdapter = new ArrayAdapter<String>(ImagePostSearchActivity.this, android.R.layout.simple_spinner_dropdown_item, topics);
                    topicSpinner.setAdapter(topicsAdapter);
                    //imposta Spinner da parametri
                    Bundle searchParams = getIntent().getExtras();
                    if(searchParams != null) {
                        String topicParam = searchParams.getString("topic");
                        if(topicParam != null) {
                            selectedTopic = topicParam;
                            topicSpinner.setSelection(topicsAdapter.getPosition(selectedTopic));
                        }
                    }
                    topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(adapterView.getItemAtPosition(i).equals("Select topic:"))
                                selectedTopic = null;
                            else
                                selectedTopic = String.valueOf(adapterView.getItemAtPosition(i));
                            if(!String.valueOf(contentSearchView.getQuery()).equals(""))
                                fetchPosts((selectedTopic == null ? "" : selectedTopic), String.valueOf(contentSearchView.getQuery()));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else
                    Toast.makeText(ImagePostSearchActivity.this, "Error while fetching topics", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(ImagePostSearchActivity.this, "Error while fetching topics: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchPosts(String topic, String keywords) {
        Call<List<Post>> getPostsByFiltersCall = apiService.getPostsByFilters(topic, keywords, "img");
        getPostsByFiltersCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    searchedPosts = new Post[response.body().size()];
                    for(int i=0; i<searchedPosts.length; i++)
                        searchedPosts[i] = response.body().get(i);
                    if(searchedPosts.length > 0)
                        noResultTextView.setVisibility(View.INVISIBLE);
                    else
                        noResultTextView.setVisibility(View.VISIBLE);
                    //Caricamento post nella GridView
                    artworksGridView.setAdapter(new PostGridAdapter(ImagePostSearchActivity.this, searchedPosts));
                    artworksGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            openPostDetails(position);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                noResultTextView.setVisibility(View.VISIBLE);
                Toast.makeText(ImagePostSearchActivity.this, "Error while searching posts: " + t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void openPostDetails(int pos) {
        Intent postListIntent = new Intent(ImagePostSearchActivity.this, PostListActivity.class);
        postListIntent.putExtra("postList", searchedPosts);
        postListIntent.putExtra("postIndex", pos);
        startActivity(postListIntent);
    }
}