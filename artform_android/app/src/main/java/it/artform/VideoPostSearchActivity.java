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

    Topic selectedTopic = null;
    Post[] searchedPosts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_post_search);

        //widget setup
        contentSearchView = findViewById(R.id.contentSearchView); //search while typing
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
            Topic topicParam = (Topic) searchParams.get("topic");
            if(topicParam != null) {
                selectedTopic = topicParam;
                ArrayAdapter topicsAdapter = (ArrayAdapter) topicSpinner.getAdapter();
                topicSpinner.setSelection(topicsAdapter.getPosition(selectedTopic));
            }
        }

        //listener barra di ricerca
        searchVideosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoSearchIntent = new Intent(VideoPostSearchActivity.this, VideoPostSearchActivity.class);
                if(selectedTopic != null)
                    videoSearchIntent.putExtra("topic", selectedTopic.getName());
                videoSearchIntent.putExtra("keywords", contentSearchView.getQuery());
                startActivity(videoSearchIntent);
            }
        });

        //listener pulsante Artisti
        searchArtistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userSearchIntent = new Intent(VideoPostSearchActivity.this, UserSearchActivity.class);
                if(selectedTopic != null)
                    userSearchIntent.putExtra("topic", selectedTopic.getName());
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
                    Topic[] topics = new Topic[response.body().size()];
                    for(int i=0; i<topics.length; i++)
                        topics[i] = response.body().get(i);
                    topicSpinner.setAdapter(new ArrayAdapter<Topic>(VideoPostSearchActivity.this, android.R.layout.simple_spinner_dropdown_item, topics));
                    topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedTopic = (Topic) adapterView.getItemAtPosition(i);
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
        Call<List<Post>> getPostsByFiltersCall = apiService.getPostsByFilters(topic, keywords, "img");
        getPostsByFiltersCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    searchedPosts = new Post[response.body().size()];
                    for(int i=0; i<searchedPosts.length; i++)
                        searchedPosts[i] = response.body().get(i);
                    //Caricamento post nella GridView
                    if(searchedPosts.length > 0) {
                        noResultTextView.setVisibility(View.INVISIBLE);
                        videosGridView.setAdapter(new PostGridAdapter(VideoPostSearchActivity.this, searchedPosts));
                        videosGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                openPostDetails(position);
                            }
                        });
                    }
                    else
                        noResultTextView.setVisibility(View.VISIBLE);
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