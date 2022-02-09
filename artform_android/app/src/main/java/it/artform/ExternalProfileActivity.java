package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExternalProfileActivity extends AppCompatActivity {
    ImageView externalProfilePic;
    TextView usernameExternalProfile;
    TextView tagsExternalProfile;
    Button notifyMeExternalProfile;
    Button badgeExternalProfile;
    Button contactMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_profile);

        externalProfilePic=findViewById(R.id.externalProfileImageView);
        usernameExternalProfile=findViewById(R.id.usernameExternalProfile);
        tagsExternalProfile=findViewById(R.id.tagsExternalProfile);
        notifyMeExternalProfile=findViewById(R.id.nofifyMeExternalProfile);
        badgeExternalProfile=findViewById(R.id.badgeButtonExternalProfile);
        contactMe=findViewById(R.id.contactmeButton);

        Button contactMe=(Button) findViewById(R.id.contactmeButton);
        contactMe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ExternalProfileActivity.this, CommissionActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }
}