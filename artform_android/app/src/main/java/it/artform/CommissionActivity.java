package it.artform;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommissionActivity extends Activity {
    Spinner topicSpinner = null;
    EditText endDateEditText = null;
    Calendar endDateCalendar = null;

    ArtformApiEndpointInterface apiService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);

        //widget setup
        TextView commissionTextView = findViewById(R.id.commissionTextView);
        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText offerEditText = findViewById(R.id.offerEditText);
        topicSpinner = findViewById(R.id.topicSpinner);
        endDateEditText = findViewById(R.id.endDateEditText);
        EditText messageEditText = findViewById(R.id.messageEditText);
        Button sumbitButton = findViewById(R.id.sumbitButton);
        Button resetButton = findViewById(R.id.resetButton);

        //web services setup
        AFGlobal app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        //imposta username utente da contattare
        Bundle userParam = getIntent().getExtras();
        if(userParam != null) {
            String username =userParam.getString("user");
            if (username != null)
                commissionTextView.append(username);
        }

        //GET dei Topic
        fetchTopics();

        //setup datepicker
        endDatePickerSetup();

        // bottone per INVIO del form
        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //////////////////////////////////////// send commission request
            }
        });

        // bottone per cancellare i campi inseriti
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

    private void fetchTopics() {
        Call<List<Topic>> getTopicsCall = apiService.getAllTopics();
        getTopicsCall.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if(response.isSuccessful()) {
                    String[] topics = new String[response.body().size()];
                    for(int i=0; i<topics.length; i++)
                        topics[i] = response.body().get(i).getName();
                    ArrayAdapter topicsAdapter = new ArrayAdapter<String>(CommissionActivity.this, android.R.layout.simple_spinner_dropdown_item, topics);
                    topicSpinner.setAdapter(topicsAdapter);
                }
                else
                    Toast.makeText(CommissionActivity.this, "Error while fetching topics: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(CommissionActivity.this, "Error while fetching topics: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void endDatePickerSetup() {
        endDateCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, month);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateEndDateLabel();
            }
        };
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CommissionActivity.this, date, endDateCalendar.get(Calendar.YEAR), endDateCalendar.get(Calendar.MONTH), endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateEndDateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ITALY);
        endDateEditText.setText(dateFormat.format(endDateCalendar.getTime()));
    }

}