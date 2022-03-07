package it.artform;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
Activity usata per cambiare USERNAME ed altre informazioni
 */

public class SettingsAccount extends Activity {
    // decleration web service
    ArtformApiEndpointInterface apiService = null;
    // decleration widget
    TextView nameTextView, surnameTextView, emailTextView;
    EditText usernameEditText, phoneEditText, passwordEditText, bioEditText;
    Button confirmButton;
    // decleration variable
    User user  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);

        // widget setup
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
                if(response.isSuccessful()) {
                    user = response.body();
                    loadUserData(); // imposta i dati nei campi
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SettingsAccount.this, "Richiesta GET dell'Utente non effettuata", Toast.LENGTH_LONG).show();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



    private void loadUserData() {
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
}
