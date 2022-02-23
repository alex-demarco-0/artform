package it.artform;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommissionActivity extends Activity {
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;
    Topic topic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);

// istanziamento widget UI
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText surnameEditText = findViewById(R.id.surnameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText detailEditText = findViewById(R.id.detailEditText);
        Spinner dateSpinner = findViewById(R.id.dateSpinner);


        // preparazione richiesta GET
        // Manbir... test
        Spinner topicSpinner = findViewById(R.id.topicSpinner);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);


        Call<List<Topic>> call = apiService.getAllTopics();
        call.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                List<Topic> myTopicList = response.body();
               /* for (Topic topic : myTopicList) {
                    String content = "";
                    content = topic.getName();
                    //topicSpinner.append(content);
                } */
                ArrayAdapter<Topic> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myTopicList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                topicSpinner.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(CommissionActivity.this, "Si è verificato un problema durante la richiesta " + t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

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