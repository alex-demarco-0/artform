package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText loginUsername = findViewById(R.id.loginUsername);
        HEAD
        Button loginButtonAccedi = findViewById(R.id.loginButtonAccedi);
        Button loginButtonRegistrati = findViewById(R.id.loginButtonRegistrati);
        EditText loginPassword = findViewById(R.id.loginPassword);



        //Button loginButton = findViewById(R.id.loginButton);


    }
}

