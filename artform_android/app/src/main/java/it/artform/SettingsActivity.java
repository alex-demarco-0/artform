package it.artform;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        arrayList.add("FAQ & Help");
        arrayList.add("Privacy");
        arrayList.add("Termini e Condizioni");
        arrayList.add("Logout");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        settingsListView.setAdapter(arrayAdapter);

        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // MIGLIORATO CON FRAGMENT.
                switch (i){
                    case 0: // post salvati
                        Intent savedPostIntent = new Intent(SettingsActivity.this, SavedPostActivity.class);
                        break;
                    case 1: // Account -- cambio username, numero di telefono, passeword - gestione notifiche e varie impostazioni -- password dimenitcata
                        Intent accountIntent = new Intent(SettingsActivity.this, SettingsAccountActivity.class);
                        startActivity(accountIntent);
                        break;
                    case 2: // FAQ -- bug report
                        openGmail();
                        break;
                    case 3: // Privacy
                        break;
                    case 4: // termini e condizioni
                        break;
                    case 5: // logout
                        logout();
                        break;
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

    private void openGmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] emails_in_to={"to@email.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, emails_in_to );
        intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(intent);
    }

    private void logout() {
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

