package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Date;

import it.artform.databases.PostDBAdapter;
import it.artform.pojos.Post;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentPubActivity extends Activity {

    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;
    Post newPost = null;

    EditText titleEditText, topicsEditText, tagsEditText;
    ImageButton publishImageButton;
    Button publishButton, cancelButton;
    ImageButton previewImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_publication);
        previewImageView = findViewById(R.id.previewImageView);
        previewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        titleEditText = findViewById(R.id.titleEditText);
        topicsEditText = findViewById(R.id.topicsEditText);
        tagsEditText = findViewById(R.id.tagsEditText);
        String username = AFGlobal.getLoggedUser();
        Date currentDate = null;
        newPost = new Post(0, username, String.valueOf(titleEditText.getText()), String.valueOf(topicsEditText.getText()), String.valueOf(tagsEditText.getText()), currentDate, 0, true, null);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        publishButton = findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContentPubActivity.this, "", Toast.LENGTH_SHORT).show();
                Call<Post> postCall = apiService.addPost(newPost);
                postCall.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        PostDBAdapter pdba = new PostDBAdapter(ContentPubActivity.this);
                        try {
                            pdba.open();
                        } catch (SQLException throwables) {
                            Toast.makeText(ContentPubActivity.this, "Si è verificato un problema dureante la pubblicazione (errore SQLite)", Toast.LENGTH_LONG).show();
                            throwables.printStackTrace();
                        }
                        pdba.createPost(newPost);
                        pdba.close();

                        Toast.makeText(ContentPubActivity.this, "Post pubblicato \uD83D\uDE02 \uD83E\uDD22", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Toast.makeText(ContentPubActivity.this, "Si è verificato un problema  :(  " + t.toString(), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}