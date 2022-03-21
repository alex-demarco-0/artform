package it.artform.activities.publication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.artform.AFGlobal;
import it.artform.R;
import it.artform.pojos.Post;
import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentPubActivity extends Activity {
    private static final int REQUEST_GET_SINGLE_FILE = 1;

    EditText titleEditText, tagsEditText;
    Button publishButton, cancelButton;
    ImageButton addImageView;
    Spinner topicSpinner;

    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;

    Post newPost = null;
    File imageFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_publication);

        //widget setup
        addImageView = findViewById(R.id.addImageView);
        titleEditText = findViewById(R.id.titleEditText);
        topicSpinner = findViewById(R.id.topicSpinner);
        tagsEditText = findViewById(R.id.tagsEditText);
        publishButton = findViewById(R.id.publishButton);
        cancelButton = findViewById(R.id.cancelButton);

        //web services setup
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        //seleziona immagine - file picker
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickImageIntent.addCategory(Intent.CATEGORY_OPENABLE);
                pickImageIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickImageIntent, "Select Image"), REQUEST_GET_SINGLE_FILE);
            }

        });
/*
        //permission dialog
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(ContentPubActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
*/
        //GET dei Topic
        fetchTopics();

        //pulsante per Pubblicare
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEditText.getText().toString().isEmpty() || tagsEditText.getText().toString().isEmpty()) {
                    Toast.makeText(ContentPubActivity.this, "Insert all values", Toast.LENGTH_SHORT).show();
                    return;
                }
                newPost = new Post(AFGlobal.getLoggedUser(), String.valueOf(titleEditText.getText()), String.valueOf(topicSpinner.getSelectedItem()), String.valueOf(tagsEditText.getText()), new Date(), 0, "img");
                uploadPost();
                finish();
            }
        });

        //pulsante per reimpostare i campi
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleEditText.getText().clear();
                topicSpinner.setSelection(0);
                tagsEditText.getText().clear();
                addImageView.setImageResource(R.drawable.ic_add);
                imageFile = null;
            }
        });
    }

    // GET Topics
    private void fetchTopics() {
        Call<List<Topic>> getTopicsCall = apiService.getAllTopics();
        getTopicsCall.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()) {
                    String[] topics = new String[response.body().size()];
                    for (int i = 0; i < topics.length; i++)
                        topics[i] = response.body().get(i).getName();
                    ArrayAdapter topicsAdapter = new ArrayAdapter<String>(ContentPubActivity.this, android.R.layout.simple_spinner_dropdown_item, topics);
                    topicSpinner.setAdapter(topicsAdapter);
                } else
                    Toast.makeText(ContentPubActivity.this, "Error while fetching topics: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(ContentPubActivity.this, "Error while fetching topics: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /////////////// TEST ALEX
    private void uploadPost() {
        //File postFile = new File("/sdcard/Download/cRGLP.jpg"); //test image
        RequestBody postResource = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part resourcePart = MultipartBody.Part.createFormData("resource", imageFile.getName(), postResource);
        String postJsonObject = formatPostObject();
        RequestBody objectPart = RequestBody.create(MediaType.parse("multipart/form-data"), postJsonObject);
        Call<Post> publishPostCall = apiService.addPost(objectPart, resourcePart);
        publishPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    /*
                    PostDBAdapter pdba = new PostDBAdapter(ContentPubActivity.this);
                    try {
                        pdba.open();
                    } catch (SQLException throwables) {
                        Toast.makeText(ContentPubActivity.this, "Si è verificato un problema durante la pubblicazione (errore SQLite)", Toast.LENGTH_LONG).show();
                        throwables.printStackTrace();
                    }
                    pdba.createPost(newPost);
                    pdba.close();
                    */
                    Toast.makeText(ContentPubActivity.this, "Post pubblicato \uD83D\uDE02 \uD83E\uDD22", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    Toast.makeText(ContentPubActivity.this, "Si è verificato un problema: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ContentPubActivity.this, "Si è verificato un problema :( " + t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private String formatPostObject() {
        String postJsonObject = new Gson().toJson(newPost);
        SimpleDateFormat oldFormat = new SimpleDateFormat("MMM d, yyyy h:mm:ss aaa");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String creationDateIndex = postJsonObject.substring(postJsonObject.indexOf("dataPubblicazione") + 20, postJsonObject.indexOf("tags") - 3);
        try {
            String newCreationDate = newFormat.format(oldFormat.parse(creationDateIndex));
            postJsonObject = postJsonObject.replace(creationDateIndex, newCreationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return postJsonObject;
    }

    // pick file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    //final String selectedImagePath = getPath(this, selectedImageUri);
                    String selectedImagePath = selectedImageUri.getPath();
                    selectedImagePath = selectedImagePath.substring(selectedImagePath.indexOf(':') + 1, selectedImagePath.length());
                    if (selectedImagePath != null) {
                        imageFile = new File(selectedImagePath);
                        selectedImageUri = Uri.fromFile(imageFile);
                    }
                    //Toast.makeText(ContentPubActivity.this, selectedImagePath.toString(), Toast.LENGTH_SHORT).show();
                    addImageView.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("ContentPubActivity", "File select ERROR", e);
        }
    }
}