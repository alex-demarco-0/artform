package it.artform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private final static String SHARED_PREFERENCES = "SharedPrefs";
    private final static String LOGIN_USER_KEY = "username";
    private final static String LOGIN_PWD_KEY = "password";
    private String username = "";
    private String password = "";
    private int checkPass = 5; // contatore credenziali errate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        // pulsante Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = loginUsername.getText().toString();
                password = loginPassword.getText().toString();
                // controllo password
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
                    Toast.makeText(LoginActivity.this, "Login effetuato", Toast.LENGTH_LONG).show();
                    checkPass = 5;
                    startActivity(mainIntent);
                    //finish();   commentato per facilitare i test
                } else {
                    Toast.makeText(LoginActivity.this, "Credenziali errate", Toast.LENGTH_LONG).show();
                    checkPass--;
                    if (checkPass == 0){
                        Toast.makeText(LoginActivity.this, "HAI SBAGLIATO PASS TROPPE VOLTE", Toast.LENGTH_LONG).show();
                        loginButton.setEnabled(false);
                    }
                }
            }
        });

        // campo per passare alla'RegisterActivity
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
