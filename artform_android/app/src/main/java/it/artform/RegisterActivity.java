package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registratiButton = findViewById(R.id.registratiButton);
    }

    registratiButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public boolean onClick(View view) {
            Intent reg = new Intent(this, MainActivity.class);
            startActivity(reg);
        }
    });
    // ahhhhhhh
    // fifo lifo
}