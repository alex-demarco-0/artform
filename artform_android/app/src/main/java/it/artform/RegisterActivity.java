package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.artform.databases.UserDBAdapter;
import it.artform.pojos.User;
import it.artform.web.ArtformApiEndpointInterface;
import it.artform.web.EmailCheckCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends Activity {
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;
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
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
            usernameEditText.setText(bundle.getString("username"));
        telefonoEditText = findViewById(R.id.telefonoEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        password2EditText = findViewById(R.id.password2EditText);
        Button registratiButton = findViewById(R.id.registratiButton);
        Button cancellaButton = findViewById(R.id.cancellaButton);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*controllo campi + toast (assegnato al metodo controllaCampi() al di fuori di onCreate())
                nome richiesto
                cognome richiesto
                email richiesto e nel formato corretto
                username richiesto
                telefono se presente deve contenere solo cifre numeriche
                password richiesto
                password2 deve corrispondere a password
                il toast deve descrivere ciò che manca
                */

                String campoMancante = controllaCampi();
                if(!campoMancante.equals("")) {
                    Toast.makeText(RegisterActivity.this, campoMancante, Toast.LENGTH_LONG).show();
                    return;
                }

                //post sul db server
                User newUser = new User(String.valueOf(nomeEditText.getText()), String.valueOf(cognomeEditText.getText()), String.valueOf(usernameEditText.getText()),
                        String.valueOf(emailEditText.getText()), String.valueOf(telefonoEditText.getText()), String.valueOf(passwordEditText.getText()), 0);
                Toast.makeText(RegisterActivity.this, newUser.toString(), Toast.LENGTH_LONG).show();
                Call<User> postUserCall = apiService.addUser(newUser);
                postUserCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        int statusCode = response.code();
                        if(statusCode == 200) {
                            Toast.makeText(RegisterActivity.this, "Registrazione effettuata con successo!", Toast.LENGTH_LONG).show();
                            //User newUser = response.body();
                            //creazione oggetto utente (attenzione a telefono nullo e punteggio)
                            //prendere da risposta server
                             /*User newUser = new User(String.valueOf(nomeEditText.getText()), String.valueOf(cognomeEditText.getText()), String.valueOf(usernameEditText.getText()),
                                        String.valueOf(emailEditText.getText()), String.valueOf(telefonoEditText.getText()), String.valueOf(passwordEditText.getText()), 0);*/
                            //post sul db locale
                            UserDBAdapter udba = new UserDBAdapter(RegisterActivity.this);
                            try {
                                udba.open();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            udba.createUser(String.valueOf(nomeEditText.getText()), String.valueOf(cognomeEditText.getText()), String.valueOf(usernameEditText.getText()),
                                    String.valueOf(emailEditText.getText()), String.valueOf(telefonoEditText.getText()), String.valueOf(passwordEditText.getText()), 0);
                            udba.close();
                            // TEST - passaggio parametri alla MainActivity (una volta superato il controllo dei campi)
                            Intent registraIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            registraIntent.putExtra("nome", "nome: " + nomeEditText.getText());
                            registraIntent.putExtra("cognome", "cognome: " + cognomeEditText.getText());
                            registraIntent.putExtra("email", "email: " + emailEditText.getText());
                            registraIntent.putExtra("username", "username: " + usernameEditText.getText());
                            if(!telefonoEditText.getText().toString().equals(""))
                                registraIntent.putExtra("telefono", "telefono: "+ telefonoEditText.getText());
                            registraIntent.putExtra("password", "password: " + passwordEditText.getText());
                            startActivity(registraIntent);
                            // TEST
                        }
                        else
                            Toast.makeText(RegisterActivity.this, "Si è verificato un problema durante la registrazione", Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Si è verificato un problema durante la registrazione", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        cancellaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomeEditText.getText().clear();
                cognomeEditText.getText().clear();
                emailEditText.getText().clear();
                usernameEditText.getText().clear();
                telefonoEditText.getText().clear();
                passwordEditText.getText().clear();
                password2EditText.getText().clear();
            }
        });
    }

    public String controllaCampi() {
        if(nomeEditText.getText().toString().equals(""))
            return "Inserisci nome";
        if(cognomeEditText.getText().toString().equals(""))
            return "Inserisci cognome";
        // verificare con con query che email e username non siano già posseduti da un utente sul DB (server)
        String email = emailEditText.getText().toString();
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(email.equals("") || !matcher.matches()) //con regular expression
            return "Inserisci email valida";
        // TEST
        Call<User> emailExists = apiService.checkEmailExists(email);
        EmailCheckCallback eccb = new EmailCheckCallback();
        emailExists.enqueue(eccb);
        String emailRes = eccb.toString();
        if(emailRes != null)
            return emailRes;
        /*
        emailExists.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200)
                    return "E-mail già esistente";
                else if(response.code() != 404)
                    return "ERROR CHECKING EMAIL";
                //Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                return "ERROR CHECKING EMAIL";
                //Toast.makeText(RegisterActivity.this, "ERROR CHECKING EMAIL", Toast.LENGTH_SHORT).show();
                //return;
            }
        });
        */
        if(usernameEditText.getText().toString().equals(""))
            return "Inserisci username";
        // qui
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