package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    int checkPass = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //istanziamento campi edit e button
        EditText loginUsername = findViewById(R.id.loginUsername);
        EditText loginPassword = findViewById(R.id.loginPassword);
        //Button loginButtonAccedi = findViewById(R.id.loginButtonAccedi);
        //Button loginButtonRegistrati = findViewById(R.id.loginButtonRegistrati);
/*
        loginButtonAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Vai alla home page
                //Toast di prova
                Toast.makeText(LoginActivity.this, "Hai premuto ACCEDI", Toast.LENGTH_LONG).show();
            }
        });

        loginButtonRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast di prova
                Toast.makeText(LoginActivity.this, "Hai premuto REGISTRATI", Toast.LENGTH_LONG).show();
                //Vai alla RegisterActivity passando come parametri username e password (se inseriti)
                Intent openRegisterActivity= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(openRegisterActivity);
            }
        });
*/
        // manbi
        Button loginButton = findViewById(R.id.loginButton);
        // controllo password
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginUsername.getText().toString().equals("admin") && loginPassword.getText().toString().equals("admin")){
                    Toast.makeText(LoginActivity.this, "Login effetutato", Toast.LENGTH_LONG).show();
                    Intent openRegisterActivity= new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(openRegisterActivity);
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
        Button goToRegister = findViewById(R.id.goToRegister);
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
