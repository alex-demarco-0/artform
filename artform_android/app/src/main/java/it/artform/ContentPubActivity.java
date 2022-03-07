package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
    private static final int REQUEST_GET_SINGLE_FILE = 0;
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;
    Post newPost = null;

    EditText titleEditText, topicsEditText, tagsEditText;
    Button publishButton, cancelButton;
    ImageButton addImageView;
    ProgressBar loadingProgressBar;

    String username = "alex";

    File imageFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_publication);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        addImageView = findViewById(R.id.addImageView);
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // INTENT 1
                /*
                Intent pickMediaIntent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickMediaIntent.setType("image/*");
                startActivityForResult(pickMediaIntent, 1);
                loadingProgressBar.setVisibility(View.VISIBLE);
               */
                // intent 2
                Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickImageIntent.addCategory(Intent.CATEGORY_OPENABLE);
                pickImageIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickImageIntent, "Select Image"), REQUEST_GET_SINGLE_FILE);
            }

        });

        titleEditText = findViewById(R.id.titleEditText);
        topicsEditText = findViewById(R.id.topicsEditText);
        tagsEditText = findViewById(R.id.tagsEditText);
/*
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContentPubActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
*/

        publishButton = findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEditText.getText().toString().isEmpty() && topicsEditText.getText().toString().isEmpty() && tagsEditText.getText().toString().isEmpty()) {
                    Toast.makeText(ContentPubActivity.this, "Insert all Values", Toast.LENGTH_SHORT).show();
                    return;
                }
                newPost = new Post(username, String.valueOf(titleEditText.getText()), String.valueOf(topicsEditText.getText()), String.valueOf(tagsEditText.getText()), new Date(), 0, "img");

                uploadPost();
                /*/Toast.makeText(ContentPubActivity.this, topicsEditText.getText().toString(), Toast.LENGTH_SHORT).show();

                RequestBody postResource = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                Toast.makeText(ContentPubActivity.this, postResource.toString(), Toast.LENGTH_LONG).show();
                uploadPost(newPost, postResource);
                 */
                // uploadPost(newPost);
                // metodo per test uploadPost(newPost);
                //                ContentPubActivity.this.uploadPost();
                //Toast.makeText(ContentPubActivity.this, "", Toast.LENGTH_SHORT).show();
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

    private void uploadPost() {
        RequestBody post = RequestBody.create(MediaType.parse("multipart/from-data"), String.valueOf(newPost));
        MultipartBody.Part resource = null;
        resource = MultipartBody.Part.createFormData("resource", newPost.getTitle(), post);
        Call<Post> publishPost = apiService.addPost(post, resource);
        publishPost.enqueue(new Callback<Post>() {
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
    }

    // intente 2
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    final String path = getPathFromUri(selectedImageUri);
                    if (path != null) {
                        File imageFile = new File(path);
                        selectedImageUri = Uri.fromFile(imageFile);
                    }
                    // CALL
                    Toast.makeText(ContentPubActivity.this, selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("ContentPubActivity", "File select ERROR", e);
        }
    }

    public String getPathFromUri(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    // metodo con URI, per POST REQUEST
    private void uploadPost(Post newPost, Uri selectedImageUri) {

        File postFile = new File(selectedImageUri.getPath());
        RequestBody postResource = RequestBody.create(MediaType.parse("multipart/form-data"), postFile);
        MultipartBody.Part resourcePart = MultipartBody.Part.createFormData("resource", postFile.getName(), postResource);
        String postJsonObject = new Gson().toJson(newPost);
        RequestBody objectPart = RequestBody.create(MediaType.parse("multipart/form-data"), postJsonObject);
        Call<Post> publishPostCall = apiService.addPost(objectPart, resourcePart);
        publishPostCall.enqueue(new Callback<Post>() {
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


    }

    private void uploadPost(Post newPost) {
        //TEST ALEX

        File postFile = new File("/sdcard/Download/cRGLP.jpg");
        RequestBody postResource = RequestBody.create(MediaType.parse("multipart/form-data"), postFile);
        MultipartBody.Part resourcePart = MultipartBody.Part.createFormData("resource", postFile.getName(), postResource);
        String postJsonObject = new Gson().toJson(newPost);
        RequestBody objectPart = RequestBody.create(MediaType.parse("multipart/form-data"), postJsonObject);
        Call<Post> publishPostCall = apiService.addPost(objectPart, resourcePart);
        publishPostCall.enqueue(new Callback<Post>() {
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

    }
}

/* test 1 MANBIr
    private void uploadPost(Post post) {
        loadingProgressBar.setVisibility(View.VISIBLE);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);



      CALL        NON FUNGE  02/03/2022
        Call<Post> postCall = apiService.addPost(username, img);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(ContentPubActivity.this, "Post aggiunto", Toast.LENGTH_SHORT).show();
                loadingProgressBar.setVisibility(View.GONE);
                Post responseAPi = response.body();

                //Toast.makeText(ContentPubActivity.this, "Titolo" + responseAPi.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ContentPubActivity.this, "Si è verificato un problema durante la richiesta " + t, Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
                    */
/*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.addImageView);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
                              */
    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULT_OK && null!=data){
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columIndex = cursor.getColumnIndex(filePathColumn[0]);
                cursor.close();

            }
        }
    */
 /*             INTENT CREATO NEL ONCREATE
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_OK);
          */
            /*
        File file = new File("/sdcard/Download/cRGLP.jpg");

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        Call<Post> call = apiService.addPost(filePart,  );
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(ContentPubActivity.this, "Post aggiunto", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ContentPubActivity.this, "Si è verificato un problema durante la richiesta " + t, Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
        */