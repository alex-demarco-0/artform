package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText nomeEditText = null;
    EditText cognomeEditText = null;
    EditText emailEditText = null;
    EditText usernameEditText = null;
    EditText telefonoEditText = null;
    EditText passwordEditText = null;
    EditText password2EditText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //istanziamento campi EditText
        nomeEditText = findViewById(R.id.nomeEditText);
        cognomeEditText = findViewById(R.id.cognomeEditText);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        password2EditText = findViewById(R.id.password2EditText);
        Button registratiButton = findViewById(R.id.registratiButton);
        //manca il pulsante 'reset'

        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*controllo campi + toast (magari assegnato ad un metodo al di fuori di onCreate)
                nome richiesto
                cognome richiesto
                email richiesto con @ e .com / .it
                username richiesto
                telefono se presente deve essere valido (solo cifre numeriche)
                password richiesto
                password2 deve corrispondere a password
                il toast deve descrivere ciò che manca
                */

                String campoMancante = controllaCampi();
                if(!campoMancante.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Inserisci" + campoMancante, Toast.LENGTH_LONG).show();
                    return;
                }

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

    public String controllaCampi() {
        if(nomeEditText.getText().equals(""))
            return "nome";
        if(cognomeEditText.getText().equals(""))
            return "cognome";
        if(emailEditText.getText().equals(""))
            return "email";
        if(usernameEditText.getText().equals(""))
            return "username";
        if(telefonoEditText.getText().equals(""))
            return "telefono corretto";
        if(passwordEditText.getText().equals(""))
            return "password";
        if(password2EditText.getText().equals(""))
            return "ripeti password";
        return "";
    }

}