package it.artform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import it.artform.web.GetLoggingUserTask;
import it.artform.web.UserCheckCallback;
import it.artform.web.UserGetCallback;
import retrofit2.Call;
import retrofit2.Callback;
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // lettura credenziali da SharedPreferences
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        username = prefs.getString(LOGIN_USER_KEY, "NO_USER");
        password = prefs.getString(LOGIN_PWD_KEY, "NO_PWD");

        TextView TESTprefs = findViewById(R.id.TESTprefs);                    // TEST
        TESTprefs.setText(username + ", " + password);

        // se presenti effettua l'accesso passando direttamente all'activity successiva
        if(!username.equals("NO_USER") && !password.equals("NO_PWD")) {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            mainIntent.putExtra("username", "username: " + username);
            mainIntent.putExtra("password", "password: " + password);
            Toast.makeText(LoginActivity.this, "Login effetuato", Toast.LENGTH_LONG).show();
            startActivity(mainIntent);
            //finish();   commentato per facilitare i test
        }

        // istanziamento campi edit e button
        EditText loginUsername = findViewById(R.id.loginUsername);
        EditText loginPassword = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButton);
        Button goToRegister = findViewById(R.id.goToRegister);
        CheckBox saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        loggingProgressBar = findViewById(R.id.loggingProgressBar);
        loggingProgressBar.setVisibility(View.INVISIBLE);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        // pulsante Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = loginUsername.getText().toString();
                password = loginPassword.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "Inserisci username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Inserisci password", Toast.LENGTH_SHORT).show();
                    return;
                }
                // controllo credenziali
                //Call<User> getLoggingUser = apiService.getUserByUsername(username);
                /*
                getLoggingUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            loggingUser = response.body();
                            Toast.makeText(LoginActivity.this, response.body() + " -- " + loggingUser.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                //UserGetCallback ugcb = new UserGetCallback(LoginActivity.this);
                //getLoggingUser.enqueue(ugcb);
                /*
                //Response<User> res = null;
                //User loggingUser = null;
                try {
                    res = getLoggingUser.execute();
                    loggingUser = res.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                 */
                //new GetLoggingUserTask(apiService, LoginActivity.this.loggingProgressBar, LoginActivity.this.loggingUser).execute(username);
                loggingProgressBar.setVisibility(View.VISIBLE);
                Call<User> getLoggingUser = apiService.getUserByUsername(username);
                try {
                    loggingUser = getLoggingUser.execute().body();
                } catch (IOException e) {
                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                loggingProgressBar.setVisibility(View.INVISIBLE);
                //Toast.makeText(LoginActivity.this, loggingUser.toString(), Toast.LENGTH_SHORT).show();
                //User loggingUser = ugcb.getUser();
                //Toast.makeText(LoginActivity.this, ugcb.getUser().toString(), Toast.LENGTH_SHORT).show();
                if (loggingUser == null) {
                    Toast.makeText(LoginActivity.this, "Utente non esistente, per favore registrati", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!loggingUser.getPassword().equals(password)) {
                    Toast.makeText(LoginActivity.this, "Password errata", Toast.LENGTH_LONG).show();
                    return;
                }
                /*
                // TEST
                if (username.equals("admin") && password.equals("admin")) {
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.putExtra("username", "username: " + username);
                    mainIntent.putExtra("password", "password: " + password);
                    // memorizzazione persistente delle credenziali (corrette) nel file SharedPreferences
                    if(saveLoginCheckBox.isChecked()) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(LOGIN_USER_KEY, username);
                        editor.putString(LOGIN_PWD_KEY, password);
                        editor.commit();
                    }
                    Toast.makeText(LoginActivity.this, "Accesso effetuato", Toast.LENGTH_LONG).show();
                    checkPass = 5;
                    startActivity(mainIntent);
                    //finish();   commentato per facilitare i test
                } else {
                    Toast.makeText(LoginActivity.this, "Credenziali errate", Toast.LENGTH_LONG).show();
                    checkPass--;
                    if (checkPass == 0){
                        Toast.makeText(LoginActivity.this, "HAI SBAGLIATO PASSWORD TROPPE VOLTE", Toast.LENGTH_LONG).show();
                        loginButton.setEnabled(false);
                    }
                }

                 */
                // TEST
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                mainIntent.putExtra("username", "username: " + username);
                mainIntent.putExtra("password", "password: " + password);
                Toast.makeText(LoginActivity.this, "Accesso effetuato", Toast.LENGTH_SHORT).show();
                startActivity(mainIntent);
            }
        });

        // campo per passare alla RegisterActivity
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openRegisterActivityIntent= new Intent(LoginActivity.this, RegisterActivity.class);
                String un = loginUsername.getText().toString(); //passaggio parametro username nella RegisterActivity se l'utente lo ha inserito
                if(!un.equals(""))
                    openRegisterActivityIntent.putExtra("username", un);
                startActivity(openRegisterActivityIntent);
            }
        });

        // campo per password dimenicata
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "ACTIVITY PASSWORD ...", Toast.LENGTH_LONG).show();
                Intent openRegisterActivity= new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(openRegisterActivity);
            }
        });
    }

}
