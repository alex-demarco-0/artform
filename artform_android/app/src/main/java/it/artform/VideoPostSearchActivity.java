package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import it.artform.feed.PostGridAdapter;
import it.artform.pojos.Post;
import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPostSearchActivity extends Activity {
    SearchView contentSearchView = null;
    Button searchArtworksButton = null;
    Button searchVideosButton = null;
    Button searchArtistsButton = null;
    Spinner topicSpinner = null;
    GridView videosGridView = null;
    TextView noResultTextView = null;

    ArtformApiEndpointInterface apiService = null;

    String selectedTopic = null;
    Post[] searchedPosts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_post_search);

        //widget setup
        contentSearchView = findViewById(R.id.contentSearchView); //search while typing
        contentSearchView.setQueryHint("Search…");
        searchArtworksButton = findViewById(R.id.searchArtworksButton); //no listener
        searchVideosButton = findViewById(R.id.searchVideosButton); //activity
        searchArtistsButton = findViewById(R.id.searchArtistsButton); //activity
        topicSpinner = findViewById(R.id.topicSpinner); //fetch topics
        videosGridView = findViewById(R.id.videosGridView); //load posts
        noResultTextView = findViewById(R.id.noResultTextView); //show only when no posts

        //web services setup
        AFGlobal app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        //GET dei Topic
        fetchTopics();

        //imposta Spinner e SearchView da parametri
        Bundle searchParams = getIntent().getExtras();
        if(searchParams != null) {
            contentSearchView.setQuery(searchParams.getCharSequence("keywords"), true);
            String topicParam = searchParams.getString("topic");
            if(topicParam != null) {
                selectedTopic = topicParam;
                ArrayAdapter topicsAdapter = (ArrayAdapter) topicSpinner.getAdapter();
                topicSpinner.setSelection(topicsAdapter.getPosition(selectedTopic));
            }
        }

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

        //listener pulsante Opere
        searchArtworksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent artworkSearchIntent = new Intent(VideoPostSearchActivity.this, ImagePostSearchActivity.class);
                if(selectedTopic != null)
                    artworkSearchIntent.putExtra("topic", selectedTopic);
                artworkSearchIntent.putExtra("keywords", contentSearchView.getQuery());
                startActivity(artworkSearchIntent);
            }
        });

        //listener pulsante Artisti
        searchArtistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userSearchIntent = new Intent(VideoPostSearchActivity.this, UserSearchActivity.class);
                if(selectedTopic != null)
                    userSearchIntent.putExtra("topic", selectedTopic);
                userSearchIntent.putExtra("keywords", contentSearchView.getQuery());
                startActivity(userSearchIntent);
            }
        });
    }

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
                    topicSpinner.setAdapter(new ArrayAdapter<String>(VideoPostSearchActivity.this, android.R.layout.simple_spinner_dropdown_item, topics));
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
                    Toast.makeText(VideoPostSearchActivity.this, "Error while fetching topics", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(VideoPostSearchActivity.this, "Error while fetching topics: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchPosts(String topic, String keywords) {
        Call<List<Post>> getPostsByFiltersCall = apiService.getPostsByFilters(topic, keywords, "vid");
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
                    videosGridView.setAdapter(new PostGridAdapter(VideoPostSearchActivity.this, searchedPosts));
                    videosGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            openPostDetails(position);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                noResultTextView.setVisibility(View.VISIBLE);
                Toast.makeText(VideoPostSearchActivity.this, "Error while searching posts: " + t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void openPostDetails(int pos) {
        Intent postListIntent = new Intent(VideoPostSearchActivity.this, PostListActivity.class);
        postListIntent.putExtra("postList", searchedPosts);
        postListIntent.putExtra("postIndex", pos);
        startActivity(postListIntent);
    }
}