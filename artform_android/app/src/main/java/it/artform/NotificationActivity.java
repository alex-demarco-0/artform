package it.artform;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import it.artform.feed.NotificationArrayAdapter;
import it.artform.pojos.Notification;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends Activity {
    //File notifFile = null;
    ListView notificationsListView = null;

    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //EditText saveNotifEditText = findViewById(R.id.saveNotifEditText);
        //Button saveNotifButton = findViewById(R.id.saveNotifButton);
        //ListView testCacheNotificheListView = findViewById(R.id.testCacheNotifListView);
/*
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
        //cacheNotifications();
 */
        notificationsListView = findViewById(R.id.notificationsListView);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        /* TEST
        Call<Integer> checkUnreadNotificationsCall = apiService.checkUnreadNotifications("arianna", new Date());
        checkUnreadNotificationsCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {

                    Toast.makeText(NotificationActivity.this, "unread: " + response.body(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(NotificationActivity.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(NotificationActivity.this, "EXC " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        */
        fetchNotifications();
    }

    private void fetchNotifications() {
        Call<List<Notification>> getAllUserNotificationsCall = apiService.getAllUserNotifications(AFGlobal.getLoggedUser());
        getAllUserNotificationsCall.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if(response.isSuccessful())
                    if (response.body().size() > 0) {
                        Notification[] userNotifications = new Notification[response.body().size()];
                        for (int i = 0; i < userNotifications.length; i++)
                            userNotifications[i] = response.body().get(i);
                        notificationsListView.setAdapter(new NotificationArrayAdapter(NotificationActivity.this, R.layout.row_notification_list, userNotifications));
                    }
                else
                    Toast.makeText(NotificationActivity.this, "Error while retrieving notifications: ERROR " + response.code(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(NotificationActivity.this, "Error while retrieving notifications: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

/*
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
/*
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
                // Do something
                Toast.makeText(getApplicationContext(), "No cached notifications", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.toString() + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return res;
    }
/*
    private void saveFile(String filename, String data) {
        try {
            FileOutputStream fos = openFileOutput(filename, this.MODE_PRIVATE|this.MODE_APPEND);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("Controller", e.getMessage() + e.getLocalizedMessage() + e.getCause());
        }
    }
*/
}