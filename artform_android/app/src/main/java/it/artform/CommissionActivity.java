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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.artform.pojos.Commission;
import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommissionActivity extends Activity {
    Spinner topicSpinner = null;
    EditText titleEditText = null;
    EditText offerEditText = null;
    EditText endDateEditText = null;
    Calendar endDateCalendar = null;
    EditText messageEditText = null;

    ArtformApiEndpointInterface apiService = null;

    String artistUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);

        //widget setup
        TextView commissionTextView = findViewById(R.id.commissionTextView);
        titleEditText = findViewById(R.id.titleEditText);
        offerEditText = findViewById(R.id.offerEditText);
        topicSpinner = findViewById(R.id.topicSpinner);
        endDateEditText = findViewById(R.id.endDateEditText);
        messageEditText = findViewById(R.id.messageEditText);
        Button sumbitButton = findViewById(R.id.sumbitButton);
        Button resetButton = findViewById(R.id.resetButton);

        //web services setup
        AFGlobal app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        //imposta username utente da contattare
        Bundle userParam = getIntent().getExtras();
        if(userParam != null) {
            String username = userParam.getString("artist");
            if (username != null) {
                commissionTextView.append(username);
                artistUsername = username;
            }
            else
                Toast.makeText(this, "Error fetching artist", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Error fetching artist", Toast.LENGTH_LONG).show();

        //GET dei Topic
        fetchTopics();

        //setup datepicker
        endDatePickerSetup();

        //pulsante per INVIO del form
        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String missingField = checkFields();
                if (!missingField.equals("")) {
                    Toast.makeText(CommissionActivity.this, missingField, Toast.LENGTH_SHORT).show();
                    return;
                }
                sendCommissionRequest();
                finish();
            }
        });

        //pulsante per reimpostare i campi
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleEditText.getText().clear();
                offerEditText.getText().clear();
                topicSpinner.setSelection(0);
                endDateEditText.getText().clear();
                messageEditText.getText().clear();
            }
        });
    }

    // GET dei Topics
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

    // crea DatePicker con calendario per la selezione della data termine
    private void endDatePickerSetup() {
        endDateCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
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

    // aggiorna endDateEditText dopo aver selezionato la data dal DatePicker
    private void updateEndDateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ITALY);
        endDateEditText.setText(dateFormat.format(endDateCalendar.getTime()));
    }

    // controllo campi
    private String checkFields() {
        if (titleEditText.getText().toString().equals(""))
            return "Insert title";
        if (offerEditText.getText().toString().equals(""))
            return "Insert offer";
        if (endDateEditText.getText().toString().equals(""))
            return "Select end date";
        if (messageEditText.getText().toString().equals(""))
            return "Write a message";
        return "";
    }

    // istanzia oggetto nuova commissione
    private Commission createCommission() {
        String title = String.valueOf(titleEditText.getText());
        double price = Double.parseDouble(String.valueOf(offerEditText.getText()));
        String description = String.valueOf(messageEditText.getText());
        String topic = topicSpinner.getSelectedItem().toString();
        Date date = new Date();
        Date endDate = endDateCalendar.getTime();
        String customerUsername = AFGlobal.getLoggedUser();
        String accountAddress =
                customerUsername.substring(0, 2) +
                artistUsername.substring(0, 2) +
                String.valueOf(offerEditText.getText()).substring(0, 1) +
                title.substring(0, 2) +
                topic.substring(0, 1) +
                date.toString().substring(2, 4); // fake address
        return new Commission(0, title, price, description, topic, date, endDate, artistUsername, customerUsername, accountAddress);
    }

    // invia nuova commissione al server
    private void sendCommissionRequest() {
        Commission newCommission = createCommission();
        Call<Commission> addCommissionCall = apiService.addCommission(newCommission);
        addCommissionCall.enqueue(new Callback<Commission>() {
            @Override
            public void onResponse(Call<Commission> call, Response<Commission> response) {
                if(response.isSuccessful())
                    Toast.makeText(CommissionActivity.this, "Commission request successfully sent to " + artistUsername, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(CommissionActivity.this, "Error while sending Commission request: ERROR " + response.code(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<Commission> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CommissionActivity.this, "Error while sending Commission requests: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}