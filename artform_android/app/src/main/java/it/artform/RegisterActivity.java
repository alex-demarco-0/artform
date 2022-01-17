package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //istanziamento campi EditText
        EditText nomeEditText = findViewById(R.id.nomeEditText);
        EditText cognomeEditText = findViewById(R.id.cognomeEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText telefonoEditText = findViewById(R.id.telefonoEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        Button registratiButton = findViewById(R.id.registratiButton);

        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //controllo campi + toast

                Intent registra = new Intent(RegisterActivity.this, MainActivity.class);
                //passaggio parametri alla MainActivity
                startActivity(registra);
            }
        });
    }

}