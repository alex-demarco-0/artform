package it.artform;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends Activity {

    File notifFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        EditText saveNotifEditText = findViewById(R.id.saveNotifEditText);
        Button saveNotifButton = findViewById(R.id.saveNotifButton);
        ListView testCacheNotificheListView = findViewById(R.id.testCacheNotifListView);

        saveNotifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notifW = saveNotifEditText.getText().toString();
                if(!notifW.equals("")) {
                    //ArrayList<String> notifR = loadFile(notifFile.toString());
                    saveFile(notifFile.toString(), notifW);
                    //update ListView
                }
            }
        });

        cacheNotifications();
    }

    // TEST
    public void cacheNotifications() {
        boolean canReadStoreExt = false;
        boolean canWriteStoreExt = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
            // Storage esterno disponibile in lettura e scrittura.
            canReadStoreExt = canWriteStoreExt = true;
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Storage esterno disponibile solo in lettura.
            canReadStoreExt = true;
            canWriteStoreExt = false;
        }
        else
            // Storage esterno non disponibile.
            canReadStoreExt = canWriteStoreExt = false;
        if(canReadStoreExt && canWriteStoreExt) {
            File dir = Environment.getExternalStorageDirectory();
            Toast.makeText(NotificationActivity.this, dir.toString(), Toast.LENGTH_LONG).show();
            //localizzazione directory 'Download' tramite API I/O di Java
            notifFile = dir;
        }
    }

    private List<String> loadFile(String filename) { //restituire lista di String da far caricare ad ArrayAdapter e alla ListView
        String line = null;
        List<String> res = null;
        try {
            InputStream in = openFileInput(filename);
            if (in != null) {
                InputStreamReader input = new InputStreamReader(in);
                BufferedReader buffreader = new BufferedReader(input);
                res = new ArrayList<>();
                while((line = buffreader.readLine()) != null)
                    res.add(line);
                in.close();
                //Toast.makeText(getApplicationContext(),"File Data == " + res,Toast.LENGTH_SHORT).show();
            }
            else {
                /* Do something*/
                Toast.makeText(getApplicationContext(), "No cached notifications", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.toString() + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return res;
    }

    private void saveFile(String filename, String data) {
        try {
            FileOutputStream fos = openFileOutput(filename, this.MODE_PRIVATE|this.MODE_APPEND);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("Controller", e.getMessage() + e.getLocalizedMessage() + e.getCause());
        }
    }
}