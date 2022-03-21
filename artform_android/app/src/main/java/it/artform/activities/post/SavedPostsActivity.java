package it.artform.activities.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import it.artform.AFGlobal;
import it.artform.R;
import it.artform.feed.PostGridAdapter;
import it.artform.pojos.Post;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedPostsActivity extends Activity {
    //web services declaration
    ArtformApiEndpointInterface apiService = null;
    AFGlobal app = null;

    // widget declaration
    GridView userSavedPostGridView = null;

    // variables declaration
    Post[] userSavedPosts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_posts);

        // widget setup
        userSavedPostGridView = findViewById(R.id.userSavedPostGridView);

        // web services setup
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        // Call dei post salvati
        Call<List<Post>> getSavedPostsCall = apiService.getUserSavedPosts(AFGlobal.getLoggedUser());
        getSavedPostsCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        userSavedPosts = new Post[response.body().size()];
                        for (int i = 0; i < userSavedPosts.length; i++)
                            userSavedPosts[i] = response.body().get(i);
                        userSavedPostGridView.setAdapter(new PostGridAdapter(SavedPostsActivity.this, userSavedPosts));
                        userSavedPostGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> position, View view, int pos, long l) {
                                openPostDetails(pos);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SavedPostsActivity.this, "Error while fetching saved Posts: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // Apri INTENT con list di post
    private void openPostDetails(int pos) {
        Intent postListIntent = new Intent(SavedPostsActivity.this, PostListActivity.class);
        postListIntent.putExtra("postList", userSavedPosts);
        postListIntent.putExtra("postIndex", pos);
        startActivity(postListIntent);
    }

}
