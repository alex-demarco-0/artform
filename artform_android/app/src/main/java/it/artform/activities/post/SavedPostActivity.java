package it.artform.activities.post;

import android.app.Activity;
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
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedPostActivity extends Activity {


    // decleration web service
    ArtformApiEndpointInterface apiService = null;
    AFGlobal app = null;

    // decleration widget
    GridView userSavedPostGridView = null;
    // decleration variable
    User loggedUser = null;
    Post[] userSavedPost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_post);
        // widget setup
        userSavedPostGridView = findViewById(R.id.userSavedPostGridView);
        // web services setup
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        // Call dei post salvati
        Call<List<Post>> getSavedPostCall = apiService.getUserSavedPosts(AFGlobal.getLoggedUser());
        getSavedPostCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        userSavedPost = new Post[response.body().size()];
                        for (int i = 0; i < userSavedPost.length; i++)
                            userSavedPost[i] = response.body().get(i);
                        userSavedPostGridView.setAdapter(new PostGridAdapter(SavedPostActivity.this, userSavedPost));
                        userSavedPostGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> position, View view, int i, long l) {
                                openPostDetails(position);
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SavedPostActivity.this, "Error while fetching user Posts: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
// Apri INTENT con list di post
    private void openPostDetails(AdapterView<?> position) {
        Toast.makeText(this, "sad" + position, Toast.LENGTH_SHORT).show();
    }

}
