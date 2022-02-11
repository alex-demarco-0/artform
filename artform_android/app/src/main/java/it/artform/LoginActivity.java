package it.artform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends Activity {
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;
    private final static String SHARED_PREFERENCES = "SharedPrefs";
    private final static String LOGIN_USER_KEY = "username";
    private final static String LOGIN_PWD_KEY = "password";
    private User loggingUser = null;
    private String username = "";
    private String password = "";
    private int checkPass = 5; // contatore credenziali errate
    ProgressBar loggingProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TEST - disabilitazione strict mode per richiesta Retrofit sincrona
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //

        // lettura credenziali da SharedPreferences
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        username = prefs.getString(LOGIN_USER_KEY, "NO_USER");
        password = prefs.getString(LOGIN_PWD_KEY, "NO_PWD");

        // TEST
        TextView TESTprefs = findViewById(R.id.TESTprefs);
        TESTprefs.setText(username + ", " + password);
        //

        // se memorizzati effettua l'accesso direttamente passando all'activity successiva
        if(!username.equals("NO_USER") && !password.equals("NO_PWD"))
            credentialsOk();

        // istanziamento widget UI
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        CheckBox saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        TextView forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        loggingProgressBar = findViewById(R.id.loggingProgressBar);
        loggingProgressBar.setVisibility(View.INVISIBLE);

        // preparazione richiesta RESTful
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        // pulsante Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "Inserisci username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Inserisci password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // controllo credenziali dal server
                loggingProgressBar.setVisibility(View.VISIBLE);
                Call<User> getLoggingUser = apiService.getUserByUsername(username);
                try {
                    Response<User> getLoggingUserResponse = getLoggingUser.execute();
                    loggingUser = getLoggingUserResponse.body();
                } catch (IOException e) {
                    Toast.makeText(LoginActivity.this, "Si è verificato un problema durante l'accesso: " + e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                loggingProgressBar.setVisibility(View.INVISIBLE);

                if (loggingUser == null) {
                    Toast.makeText(LoginActivity.this, "Utente non esistente, per favore registrati", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!loggingUser.getPassword().equals(password)) {
                    if (checkPass == 0) {
                        Toast.makeText(LoginActivity.this, "Hai sbagliato password troppe volte", Toast.LENGTH_LONG).show();
                        loginButton.setEnabled(false);
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "Password errata", Toast.LENGTH_LONG).show();
                    checkPass--;
                    return;
                }
                // credenziali OK
                // memorizzazione persistente delle credenziali corrette nel file SharedPreferences se checkBox spuntato
                if(saveLoginCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(LOGIN_USER_KEY, username);
                    editor.putString(LOGIN_PWD_KEY, password);
                    editor.commit();
                }
                credentialsOk();
            }
        });

        // pulsante per passare alla RegisterActivity
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openRegisterActivityIntent= new Intent(LoginActivity.this, RegisterActivity.class);
                String un = usernameEditText.getText().toString(); //passaggio parametro username nella RegisterActivity se l'utente lo ha inserito
                if(!un.equals(""))
                    openRegisterActivityIntent.putExtra("username", un);
                startActivity(openRegisterActivityIntent);
            }
        });

        // pulsante per password dimenicata
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "ACTIVITY PASSWORD ...", Toast.LENGTH_LONG).show();
                Intent openRegisterActivity= new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(openRegisterActivity);
            }
        });
    }

    // metodo per passare alla MainActivity una volta che le credenziali sono corrette
    private void credentialsOk() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        // TEST
        mainIntent.putExtra("username", "username: " + username);
        mainIntent.putExtra("password", "password: " + password);
        //
        Toast.makeText(LoginActivity.this, "Benvenuto " + username, Toast.LENGTH_LONG).show();
        startActivity(mainIntent);
        //finish();   //commentato per facilitare i test
    }

}
