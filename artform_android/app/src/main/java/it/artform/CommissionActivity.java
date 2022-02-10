package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CommissionActivity extends Activity {
    Button confirmCommission = null;
    EditText info = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);
        confirmCommission = findViewById(R.id.confirmCommission);
        info = findViewById(R.id.info);
        confirmCommission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                startActivity(emailIntent);
            }
        });
    }
}