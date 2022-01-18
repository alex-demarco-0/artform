package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    int checkPass = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText loginUsername = findViewById(R.id.loginUsername);
        EditText loginPassword = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginUsername.getText().toString().equals("admin") && loginPassword.getText().toString().equals("admin")){
                    Toast.makeText(LoginActivity.this, "Login effetutato", Toast.LENGTH_LONG).show();
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

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsername.getText().clear();
                loginPassword.getText().clear();
            }
        });


    }
}