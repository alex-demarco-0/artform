package it.artform.activities.notifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import it.artform.AFGlobal;
import it.artform.activities.commission.CommissionRequestActivity;
import it.artform.activities.publication.ContentPubActivity;
import it.artform.activities.homepage.MainActivity;
import it.artform.activities.post.PostListActivity;
import it.artform.R;
import it.artform.activities.search.ImagePostSearchActivity;
import it.artform.activities.search.UserSearchActivity;
import it.artform.activities.profile.UserProfileActivity;
import it.artform.feed.NotificationArrayAdapter;
import it.artform.pojos.Notification;
import it.artform.pojos.Post;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends Activity {
    //File notifFile = null;
    ListView notificationsListView = null;
    BottomNavigationView bottomNavigationView = null;

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
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);

        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);
        fetchNotifications();
    }

    // listener NavigationBar
    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.home_item:
                    Intent homeIntent = new Intent(NotificationActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    break;
                case R.id.search_item:
                    Intent searchIntent = new Intent(NotificationActivity.this, ImagePostSearchActivity.class);
                    startActivity(searchIntent);
                    break;
                case R.id.add_item:
                    Intent publishIntent = new Intent(NotificationActivity.this, ContentPubActivity.class);
                    startActivity(publishIntent);
                    break;
                case R.id.notifications_item:
                    break;
                case R.id.profile_item:
                    Intent userProfileIntent = new Intent(NotificationActivity.this, UserProfileActivity.class);
                    startActivity(userProfileIntent);
                    break;
            }
            return false;
        }
    };

    private void fetchNotifications() {
        Call<List<Notification>> getAllUserNotificationsCall = apiService.getAllUserNotifications(AFGlobal.getLoggedUser());
        getAllUserNotificationsCall.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if(response.isSuccessful())
                    if (response.body().size() > 0) {
                        Notification[] userNotifications = new Notification[response.body().size()];
                        for (int i = userNotifications.length - 1; i >= 0; i--)
                            userNotifications[i] = response.body().get(i);
                        notificationsListView.setAdapter(new NotificationArrayAdapter(NotificationActivity.this, R.layout.row_notification_list, userNotifications));
                        notificationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                openNotification(userNotifications[i]);
                            }
                        });
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

    private void openNotification(Notification notification) {
        switch(notification.getCategory()) {
            case 1:
            case 2:
                Call<Post> getPostCall = apiService.getPost(Integer.parseInt(notification.getLink()));
                getPostCall.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if(response.isSuccessful()) {
                            Intent postDetailsIntent = new Intent(NotificationActivity.this, PostListActivity.class);
                            Post[] post = {response.body()};
                            postDetailsIntent.putExtra("postList", post);
                            startActivity(postDetailsIntent);
                        }
                        else
                            Toast.makeText(NotificationActivity.this, "Error while opening notification details: ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(NotificationActivity.this, "Error while opening notification details: " + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 3:
                // WIP
                Intent commissionRequestIntent = new Intent(NotificationActivity.this, CommissionRequestActivity.class);
                commissionRequestIntent.putExtra("commissionId", Integer.parseInt(notification.getLink()));
                if(notification.getDescription().contains("accepted"))
                    commissionRequestIntent.putExtra("status", "accepted");
                else if(notification.getDescription().contains("refused"))
                    commissionRequestIntent.putExtra("status", "refused");
                startActivity(commissionRequestIntent);
                break;
            case 4:
            case 5:
                Intent userProfileIntent = new Intent(NotificationActivity.this, UserProfileActivity.class);
                startActivity(userProfileIntent);
                break;
            default:
                Toast.makeText(NotificationActivity.this, "Invalid notification", Toast.LENGTH_SHORT).show();
        }
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