package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;

import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
Activity usata per cambiare USERNAME ed altre informazioni
 */

public class SettingsAccountActivity extends Activity {
    private static final int REQUEST_GET_SINGLE_FILE = 0;

    // decleration web service
    ArtformApiEndpointInterface apiService = null;
    // decleration widget
    TextView nameTextView, surnameTextView, emailTextView;
    EditText usernameEditText, phoneEditText, passwordEditText, bioEditText;
    Button confirmButton;
    ImageView userProfilePicImageView;
    // decleration variable
    User user = null;
    User modUser = null;
    File imageFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);

        // widget setup
        userProfilePicImageView = findViewById(R.id.userProfilePicImageView);
        nameTextView = findViewById(R.id.nameTextView);
        surnameTextView = findViewById(R.id.surnameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        bioEditText = findViewById(R.id.bioEditText);
        confirmButton = findViewById(R.id.confirmButton);

        // web services setup
        AFGlobal app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        Call<User> getUserCall = apiService.getUserByUsername(AFGlobal.getLoggedUser());
        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    loadUserData(); // imposta i dati nei campi
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SettingsAccountActivity.this, "Richiesta GET dell'Utente non effettuata", Toast.LENGTH_LONG).show();
            }
        });

        // apre galleria per modificare immagine --- ---- ---- NON IMPLEMENTATO yet
        userProfilePicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickImageIntent.addCategory(Intent.CATEGORY_OPENABLE);
                pickImageIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickImageIntent, "Select Image"), REQUEST_GET_SINGLE_FILE);
            }
        });

        // metodo che modifica i file

        // bottone per confermare il cambiamento
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cambio delle varibili
                usernameEditText.setText(usernameEditText.getText());
                phoneEditText.setText(phoneEditText.getText());
                passwordEditText.setText(passwordEditText.getText());
                bioEditText.setText(bioEditText.getText());

                // creazione user modificato
                modUser = new User(user.getName(), user.getSurname(), usernameEditText.getText().toString(), user.getEmail(), phoneEditText.getText().toString(), passwordEditText.getText().toString(), bioEditText.getText().toString(), user.getPoints());
                updateUser();
                finish();
            }
        });

        // bottone per eliminare account
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });
    }
    // non funziona per le chiavi esterne
    private void deleteUser() {
        Call<ResponseBody> deleteUser = apiService.deleteUser(user.getUsername());
        deleteUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                    Toast.makeText(SettingsAccountActivity.this, "User: " + user.getName() + " DELETED", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SettingsAccountActivity.this, "Errore while deleting user: ERROR" + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SettingsAccountActivity.this, "Error while deleting User: ERROR" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // richiesta PUT
    private void updateUser(){
        // modifiche utente
        String userJsonObject = new Gson().toJson(modUser);
        RequestBody objectPart = RequestBody.create(MediaType.parse("multipart/form-data"), userJsonObject);
        // immagine
        RequestBody postResource = RequestBody.create(MediaType.parse("multipart/from-data"), imageFile);
        MultipartBody.Part resourcePart = MultipartBody.Part.createFormData("resource", imageFile.getName(), postResource);
        // call
        Call<User> updateUser = apiService.updateUser(user.getUsername(), objectPart, resourcePart);
        updateUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SettingsAccountActivity.this, "Utente modificato \uD83D\uDE02 \uD83E\uDD22", Toast.LENGTH_LONG).show();
                    finish();
                }else
                    Toast.makeText(SettingsAccountActivity.this, "Si è verificato un problema: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SettingsAccountActivity.this, "Si è verificato un problema \uD83E\uDE21" + t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }


    //inserimento automatico dei dati.
    private void loadUserData() {
        // immagine del profilo
        String profilePicUri = AFGlobal.USER_PROPIC_PATH + AFGlobal.getLoggedUser() + ".jpg";
        Picasso.get().load(profilePicUri).into(SettingsAccountActivity.this.userProfilePicImageView);
        // nome
        nameTextView.setText(user.getName());
        // cognome
        surnameTextView.setText(user.getSurname());
        // username
        usernameEditText.setText(user.getUsername());
        // email
        emailTextView.setText(user.getEmail());
        // phone number
        phoneEditText.setText(user.getPhone());
        // password
        passwordEditText.setText(user.getPassword());
        // bio
        bioEditText.setText(user.getBio());
    }

    // per prendere immagine
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    String selectedImagePath = selectedImageUri.getPath();
                    selectedImagePath = selectedImagePath.substring(selectedImagePath.indexOf(':') + 1, selectedImagePath.length());
                    if (selectedImagePath != null) {
                        imageFile = new File(selectedImagePath);
                        selectedImageUri = Uri.fromFile(imageFile);
                    }
                    userProfilePicImageView.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("SettingsAccountActivity", "Image select ERROR", e);
        }
    }
}
