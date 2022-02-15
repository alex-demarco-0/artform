package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        badgeExternalProfile=findViewById(R.id.badgeButtonExternalProfile);

        contactMe=(Button) findViewById(R.id.contactmeButton);
        contactMe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ExternalProfileActivity.this, CommissionActivity.class);
                        startActivity(intent);
                    }
                }
        ); 

        notifyMeExternalProfile=(Button) findViewById(R.id.nofifyMeExternalProfile);
        notifyMeExternalProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Toast.makeText(ExternalProfileActivity.this, "Hai attivato le notifiche per l'utente *nome utente* ", Toast.LENGTH_LONG).show();
                    }
                }

        );

    }
}