package it.artform;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import it.artform.pojos.Commission;
import it.artform.pojos.Notification;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommissionRequestActivity extends Activity {
    TextView requestTextView = null;
    TextView titleTopicTextView = null;
    TextView messageTextView = null;
    TextView offerTextView = null;
    TextView endDateTextView = null;
    Button acceptButton = null;
    Button refuseButton = null;

    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;

    Commission commission = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_request);

        //widget setup
        requestTextView = findViewById(R.id.requestTextView);
        titleTopicTextView = findViewById(R.id.titleTopicTextView);
        messageTextView = findViewById(R.id.messageTextView);
        messageTextView.setMovementMethod(new ScrollingMovementMethod());
        offerTextView = findViewById(R.id.offerTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        acceptButton = findViewById(R.id.acceptButton);
        refuseButton = findViewById(R.id.refuseButton);

        //web services setup
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        Bundle commissionParam = getIntent().getExtras();
        if(commissionParam != null)
            fetchComission(commissionParam.getInt("commissionId"));

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDecision(true);
            }
        });

        refuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDecision(false);
            }
        });
    }

    private void fetchComission(int id) {
        Call<Commission> getCommissionCall = apiService.getCommission(id);
        getCommissionCall.enqueue(new Callback<Commission>() {
            @Override
            public void onResponse(Call<Commission> call, Response<Commission> response) {
                if(response.isSuccessful()) {
                    commission = response.body();
                    if(commission.getCustomer().equals(AFGlobal.getLoggedUser())) {
                        acceptButton.setVisibility(View.INVISIBLE);
                        refuseButton.setVisibility(View.INVISIBLE);
                        String status = getIntent().getExtras().getString("status");
                        requestTextView.setText("Your commission request to " + commission.getArtist() + " was " + status + ".");
                    }
                    else
                        requestTextView.setText(commission.getCustomer() +  " requested you a commission:");
                    titleTopicTextView.setText(commission.getTitle() + " (" + commission.getTopic() + ")");
                    messageTextView.setText(commission.getDescription());
                    offerTextView.setText("Offer: " + commission.getPrice() + "$");
                    endDateTextView.setText("End date: " + commission.getEndDate().toString());
                }
                else
                    Toast.makeText(CommissionRequestActivity.this, "Error while fetching commission: ERROR " + response.code(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Commission> call, Throwable t) {
                t.toString();
                Toast.makeText(CommissionRequestActivity.this, "Error while fetching commission: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendDecision(boolean decision) {
        String description = commission.getArtist() + (decision ? " accepted" : " refused") + " your commission";
        Notification decisionNotif = new Notification(new Date(), 3, description, String.valueOf(commission.getId()), commission.getCustomer());
        Call<Notification> addNotificationCall = apiService.addNotification(decisionNotif);
        addNotificationCall.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(CommissionRequestActivity.this, "Commission" + (decision ? " accepted" : " refused"), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(CommissionRequestActivity.this, "Error while sending decision: ERROR " + response.code(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                t.toString();
                Toast.makeText(CommissionRequestActivity.this, "Error while sending decision: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}