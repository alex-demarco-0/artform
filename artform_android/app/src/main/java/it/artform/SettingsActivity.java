package it.artform;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SettingsActivity extends Activity {
    ListView settingsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        //ListView dei settings
        settingsListView = findViewById(R.id.settingsListView);
        // inizializzazione
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Saved post");
        arrayList.add("Account");
        arrayList.add("Privacy");
        arrayList.add("Sicurezza");
        arrayList.add("Assistenza");
        arrayList.add("Informazioni");
        arrayList.add("Logout");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        settingsListView.setAdapter(arrayAdapter);

        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                switch (i){
                    case 0:

                        break;
                    case 1:
                        Intent accountIntent = new Intent(SettingsActivity.this, SettingsAccount.class);
                        startActivity(accountIntent);
                        break;
                    case 6:
                        break;
                }

                // logout
                if (i == 6) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setTitle("Confirmation PopUp!").
                            setMessage("You sure, that you want to logout?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(SettingsActivity.this, "AHHHHHHHh", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            }
        });

        // logout button
        /*
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    */
    }
}

