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

    private final static String MY_PREFERENCES = "MyPref";
    private final static String LOGIN_USER_KEY = "username";
    private final static String LOGIN_PWD_KEY = "password";
    private int checkPass = 5; // counter credenziali errate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // lettura credenziali da SharedPreferences
        SharedPreferences prefs = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String username = prefs.getString(LOGIN_USER_KEY, "NO_USER");
        String password = prefs.getString(LOGIN_PWD_KEY, "NO_PWD");
        // se presenti effettua l'accesso passando direttamente all'activity successiva
        if(!username.equals("NO_USER") && !password.equals("NO_PWD")) {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            mainIntent.putExtra("username", "username: " + username);
            mainIntent.putExtra("password", "password: " + password);
            Toast.makeText(LoginActivity.this, "Login effetuato", Toast.LENGTH_LONG).show();
            startActivity(mainIntent);
        }

        // istanziamento campi edit e button
        EditText loginUsername = findViewById(R.id.loginUsername);
        EditText loginPassword = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButton);
        Button goToRegister = findViewById(R.id.goToRegister);
        CheckBox saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);

        // pulsante Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // controllo password
                if (loginUsername.getText().toString().equals("admin") && loginPassword.getText().toString().equals("admin")){
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.putExtra("username", "username: " + loginUsername.getText());
                    mainIntent.putExtra("password", "password: " + loginPassword.getText());
                    // memorizzazione persistente delle credenziali (corrette) nel file SharedPreferences
                    if(saveLoginCheckBox.isChecked()) {
                        SharedPreferences prefs = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(LOGIN_USER_KEY, username);
                        editor.putString(LOGIN_PWD_KEY, password);
                        editor.commit();
                    }
                    Toast.makeText(LoginActivity.this, "Login effetuato", Toast.LENGTH_LONG).show();
                    startActivity(mainIntent);
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

        // campo per andare all'Activity Register
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
        TextView forgotPassword = findViewById(R.id.forgotPassword);
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
