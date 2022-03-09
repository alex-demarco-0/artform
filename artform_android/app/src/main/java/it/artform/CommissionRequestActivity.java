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
        Button acceptButton = findViewById(R.id.acceptButton);
        Button refuseButton = findViewById(R.id.refuseButton);

        //web services setup
        AFGlobal app = (AFGlobal) getApplication();
        ArtformApiEndpointInterface apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        Bundle commissionParam = getIntent().getExtras();
        if(commissionParam != null)
            fetchComission(commissionParam.getInt("commissionId"));

        if(commission == null) return;

        requestTextView.setText(commission.getCustomer() +  "requested you a commission:");
        titleTopicTextView.setText(commission.getTitle() + " (" + commission.getTopic() + ")");
        messageTextView.setText(commission.getDescription());
        offerTextView.setText("Offer: " + commission.getPrice() + "$");
        endDateTextView.setText(commission.getEndDate().toString());

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
                if(response.isSuccessful())
                    commission = response.body();
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
        Notification decisionNotif = new Notification(new Date(), 6, description, "", commission.getCustomer());
    }

}