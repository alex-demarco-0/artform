package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.artform.databases.UserDBAdapter;
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;
    EditText nameEditText = null;
    EditText surnameEditText = null;
    EditText emailEditText = null;
    EditText usernameEditText = null;
    EditText phoneEditText = null;
    EditText passwordEditText = null;
    EditText password2EditText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //istanziamento campi EditText
        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            usernameEditText.setText(bundle.getString("username"));
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        password2EditText = findViewById(R.id.password2EditText);
        Button registerButton = findViewById(R.id.registerButton);
        Button clearButton = findViewById(R.id.clearButton);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                controllo campi + Toast (assegnato al metodo checkFields() al di fuori di onCreate())
                nome richiesto
                cognome richiesto
                email richiesto, nel formato corretto e non presente sul Database
                username richiesto e non presente sul Database
                telefono facoltativo, se inserito deve contenere solo cifre numeriche
                password richiesto
                password2 deve corrispondere a password
                il Toast deve dare avviso all'utente del calmpo mancante/incorretto
                */
                String missingField = checkFields();
                if (!missingField.equals("")) {
                    Toast.makeText(RegisterActivity.this, missingField, Toast.LENGTH_LONG).show();
                    return;
                }

                // creazione oggetto nuovo utente
                User newUser = new User(String.valueOf(nameEditText.getText()), String.valueOf(surnameEditText.getText()),
                        String.valueOf(usernameEditText.getText()), String.valueOf(emailEditText.getText()),
                        String.valueOf(phoneEditText.getText()), String.valueOf(passwordEditText.getText()), "", 0);

                // richiesta POST sul Database server
                Call<User> postUserCall = apiService.addUser(newUser);
                postUserCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            //post sul db locale
                            UserDBAdapter udba = new UserDBAdapter(RegisterActivity.this);
                            try {
                                udba.open();
                            } catch (SQLException throwables) {
                                Toast.makeText(RegisterActivity.this, "Si è verificato un problema durante la registrazione (errore SQLite)", Toast.LENGTH_LONG).show();
                                throwables.printStackTrace();
                            }
                            udba.createUser(newUser);
                            udba.close();

                            Toast.makeText(RegisterActivity.this, "Registrazione effettuata con successo!", Toast.LENGTH_LONG).show();

                            Intent mainIntent = new Intent(RegisterActivity.this, TopicUserActivity.class);
                            // TEST - passaggio parametri alla MainActivity (una volta superato il controllo dei campi)
                            mainIntent.putExtra("nome", "nome: " + nameEditText.getText());
                            mainIntent.putExtra("cognome", "cognome: " + surnameEditText.getText());
                            mainIntent.putExtra("email", "email: " + emailEditText.getText());
                            mainIntent.putExtra("username", "username: " + usernameEditText.getText());
                            if (!phoneEditText.getText().toString().equals(""))
                                mainIntent.putExtra("telefono", "telefono: " + phoneEditText.getText());
                            mainIntent.putExtra("password", "password: " + passwordEditText.getText());
                            //
                            AFGlobal.setLoggedUser(String.valueOf(usernameEditText.getText()));
                            startActivity(mainIntent);
                            //finish();
                        } else
                            Toast.makeText(RegisterActivity.this, "Si è verificato un problema durante la registrazione: ERROR " + response.code(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Si è verificato un problema durante la registrazione: " + t.toString(), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEditText.getText().clear();
                surnameEditText.getText().clear();
                emailEditText.getText().clear();
                usernameEditText.getText().clear();
                phoneEditText.getText().clear();
                passwordEditText.getText().clear();
                password2EditText.getText().clear();
            }
        });
    }

    private String checkFields() {
        // nome
        if (nameEditText.getText().toString().equals(""))
            return "Inserisci nome";
        // cognome
        if (surnameEditText.getText().toString().equals(""))
            return "Inserisci cognome";
        // email
        String email = emailEditText.getText().toString();
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (email.equals("") || !matcher.matches()) //con regular expression
            return "Inserisci email valida";
        // verifica che l'email non sia già posseduta da un utente sul Database server
        Call<User> emailExists = apiService.checkEmailExists(email);
        User existingUser = null;
        try {
            existingUser = emailExists.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return "Problema durante la verifica dell'email: " + e.toString();
        }
        if (existingUser != null)
            return "Email già esistente";
        // username
        String username = usernameEditText.getText().toString();
        if (username.equals(""))
            return "Inserisci username";
        // verifica che l'username non sia già posseduto da un utente sul Database server
        Call<User> usernameExists = apiService.getUserByUsername(username);
        existingUser = null;
        try {
            existingUser = usernameExists.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return "Problema durante la verifica dell'username: " + e.toString();
        }
        if (existingUser != null)
            return "Username già esistente";
        // telefono
        String phone = phoneEditText.getText().toString();
        if (!phone.equals(""))
            try {
                Double.parseDouble(phone);
            } catch (NumberFormatException e) {
                return "Inserisci un numero telefonico corretto";
            }
        // password
        String pw = passwordEditText.getText().toString();
        if (pw.equals(""))
            return "Inserisci password";
        // ripeti password
        String pw2 = password2EditText.getText().toString();
        if (pw2.equals(""))
            return "Ripeti password";
        if (!pw.equals(pw2)) {
            password2EditText.getText().clear();
            return "Le password non corrispondono";
        }
        return "";
    }

}