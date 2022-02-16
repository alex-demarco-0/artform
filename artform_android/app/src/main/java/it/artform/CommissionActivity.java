package it.artform;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Retrofit;

public class CommissionActivity extends Activity {
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);

// istanziamento widget UI
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText surnameEditText = findViewById(R.id.surnameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText detailEditText = findViewById(R.id.detailEditText);
        Spinner topicSpinner = findViewById(R.id.topicSpinner);
        Spinner dateSpinner = findViewById(R.id.dateSpinner);



        // preparazione richiesta RESTful
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);



        // bottone per INVIO del form
        Button sumbitButton = findViewById(R.id.sumbitButton);
        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        //Spinner che prende in input i topics dal database



        // bottone per cancellare i campi inseriti
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEditText.getText().clear();
                surnameEditText.getText().clear();
                emailEditText.getText().clear();
                detailEditText.getText().clear();
            }
        });


    }

}