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
        EditText password2EditText = findViewById(R.id.password2EditText);
        Button registratiButton = findViewById(R.id.registratiButton);

        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*controllo campi + toast
                nome richiesto
                cognome richiesto
                email richiesto con @ e .com / .it
                username richiesto
                password richiesto
                password2 deve corrispondere a password
                il toast deve descrivere ciò che manca
                */

                //passaggio parametri alla MainActivity (una volta che i campi sono OK)
                Intent registraIntent = new Intent(RegisterActivity.this, MainActivity.class);
                registraIntent.putExtra("nome", nomeEditText.getText());
                registraIntent.putExtra("cognome", cognomeEditText.getText());
                registraIntent.putExtra("email", emailEditText.getText());
                registraIntent.putExtra("username", usernameEditText.getText());
                if(!telefonoEditText.getText().equals(""))
                    registraIntent.putExtra("telefono", telefonoEditText.getText());
                registraIntent.putExtra("password", passwordEditText.getText());
                startActivity(registraIntent);
            }
        });
    }

}