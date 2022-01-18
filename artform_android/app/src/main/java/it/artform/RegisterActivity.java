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
                /*controllo campi + toast (assegnato al metodo controllaCampi() al di fuori di onCreate())
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
                    Toast.makeText(RegisterActivity.this, campoMancante, Toast.LENGTH_LONG).show();
                    return;
                }

                //passaggio parametri alla MainActivity (solo una volta superato il controllo dei campi)
                Intent registraIntent = new Intent(RegisterActivity.this, MainActivity.class);
                registraIntent.putExtra("nome", "nome: " + nomeEditText.getText());
                registraIntent.putExtra("cognome", "cognome: " + cognomeEditText.getText());
                registraIntent.putExtra("email", "email: " + emailEditText.getText());
                registraIntent.putExtra("username", "username: " + usernameEditText.getText());
                if(!telefonoEditText.getText().equals(""))
                    registraIntent.putExtra("telefono", "telefono: "+ telefonoEditText.getText());
                registraIntent.putExtra("password", "password: " + passwordEditText.getText());
                startActivity(registraIntent);
            }
        });
    }

    public String controllaCampi() {
        if(nomeEditText.getText().toString().equals(""))
            return "Inserisci nome";
        if(cognomeEditText.getText().toString().equals(""))
            return "Inserisci cognome";
        String email = emailEditText.getText().toString();
        if(email.equals("") || !email.contains("@") || !email.contains(".com") || !email.contains(".it")) //meglio con regular expression ^(.+)@(.+)$
            return "Inserisci email valida";
        if(usernameEditText.getText().toString().equals(""))
            return "Inserisci username";
        String tel = telefonoEditText.getText().toString();
        if(!tel.equals(""))
            try {
                Double.parseDouble(tel);
            } catch(NumberFormatException e) {
                return "Inserisci un telefono corretto";
            }
        String pw = passwordEditText.getText().toString();
        if(pw.equals(""))
            return "Inserisci password";
        String pw2 = password2EditText.getText().toString();
        if(pw2.equals(""))
            return "Ripeti password";
        if(!pw.equals(pw2))
            return "Le password non corrispondono";
        return "";
    }

}