package it.artform;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView commissionTextView = findViewById(R.id.commissionTextView);
        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText offerEditText = findViewById(R.id.offerEditText);
        Spinner topicSpinner = findViewById(R.id.topicSpinner);
        EditText endDateEditText = findViewById(R.id.endDateEditText);
        EditText messageEditText = findViewById(R.id.messageEditText);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        Call<List<Topic>> call = apiService.getAllTopics();
        call.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                List<Topic> myTopicList = response.body();
                ArrayList<String> stringTopic = new ArrayList<>();
                for (Topic topic : myTopicList) {
                    String content = "";
                    content = topic.getName();
                    stringTopic.add(content);
                    //topicSpinner.append(content);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, stringTopic);
                topicSpinner.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(CommissionActivity.this, "Si è verificato un problema durante la richiesta " + t, Toast.LENGTH_LONG).show();
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
                titleEditText.getText().clear();
                offerEditText.getText().clear();
                endDateEditText.getText().clear();
                messageEditText.getText().clear();
            }
        });
    }

}