package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //istanziamento campi edit e button
        EditText loginUsername = findViewById(R.id.loginUsername);

        Button loginButtonAccedi = findViewById(R.id.loginButtonAccedi);
        Button loginButtonRegistrati = findViewById(R.id.loginButtonRegistrati);

        EditText loginPassword = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButtonAccedi);

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
                //Vai alla RegisterActivity
                Intent openRegisterActivity= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(openRegisterActivity);
            }


        });

        loginButtonAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginUsername.getText().toString().equals("admin") && loginPassword.getText().toString().equals("admin")){
                    Toast.makeText(LoginActivity.this, "Login effetutato", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenziali errate", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}