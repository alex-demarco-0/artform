package it.artform;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsActivity extends Activity {
    ListView settingsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        //ListView dei settings
        settingsListView = (ListView) findViewById(R.id.settingsListView);
        // inizializzazione
        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add("Saved post");
        arrayList.add("Account");
        arrayList.add("Privacy");
        arrayList.add("Sicurezza");
        arrayList.add("Assistenza");
        arrayList.add("Informazioni");

        TextView logoutTextView = findViewById(R.id.logoutTextView);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                moveTaskToBack(true);
            }
        });
    }
}