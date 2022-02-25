package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;

import it.artform.databases.PostDBAdapter;
import it.artform.pojos.Post;
import it.artform.web.ArtformApiEndpointInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    ImageButton addImageView;
    ImageView previewImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_publication);
        addImageView = findViewById(R.id.addImageView);
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        titleEditText = findViewById(R.id.titleEditText);
        topicsEditText = findViewById(R.id.topicsEditText);
        tagsEditText = findViewById(R.id.tagsEditText);
        String username = AFGlobal.getLoggedUser();
        Date currentDate = null;
        newPost = new Post(0, username, String.valueOf(titleEditText.getText()), String.valueOf(topicsEditText.getText()), String.valueOf(tagsEditText.getText()), currentDate, 0, true);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        publishButton = findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentPubActivity.this.uploadPost();
                Toast.makeText(ContentPubActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private uploadPost() {
        File postFile = FileUtils.getFile(this, fileUri);
        RequestBody postResource = RequestBody.create(MediaType.parse("multipart/form-data"), postFile);
        MultipartBody.Part resourcePart = MultipartBody.Part.createFormData("resource", file.getName(), postResource);
        String postJsonObject = new Gson().toJson(newPost);
        RequestBody objectPart = RequestBody.create(MediaType.parse("multipart/form-data"), postJsonObject);
        //MultipartBody.Part objectPart = MultipartBody.Part.createFormData("postObj", "", postObject);
        //RequestBody postObj = RequestBody.create(okhttp3.MultipartBody.FORM, newPost);
        Call<Post> postCall = apiService.addPost(objectPart, resourcePart);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                PostDBAdapter pdba = new PostDBAdapter(ContentPubActivity.this);
                try {
                    pdba.open();
                } catch (SQLException throwables) {
                    Toast.makeText(ContentPubActivity.this, "Si è verificato un problema durante la pubblicazione (errore SQLite)", Toast.LENGTH_LONG).show();
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

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titleEditText.getText().clear();
                topicsEditText.getText().clear();
                tagsEditText.getText().clear();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri selectedImage  = data.getData();
            addImageView.setVisibility(View.GONE);
            previewImageView = findViewById(R.id.previewImageView);
            previewImageView.setImageURI(selectedImage);
        }
    }

}