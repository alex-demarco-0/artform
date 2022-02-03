package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.Date;

import it.artform.databases.PostCursorAdapter;
import it.artform.databases.PostsDBAdapter;
import it.artform.pojos.Post;
import it.artform.feed.PostArrayAdapter;

public class MainActivity extends Activity {
    PostsDBAdapter pdba = null;
    Button settingsButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TEST - dati registrazione
        ListView datiRegistrazioneListView = findViewById(R.id.datiRegistrazioneListView);

        Bundle datiRegistrazione = getIntent().getExtras();
        if(datiRegistrazione != null) {
            String[] listaDatiRegistrazione = new String[datiRegistrazione.size()];
            int i = 0;
            for (String key : datiRegistrazione.keySet()) {
                if (datiRegistrazione.get(key) != null)
                    listaDatiRegistrazione[i] = datiRegistrazione.get(key).toString();
                i++;
            }
            ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDatiRegistrazione);
            datiRegistrazioneListView.setAdapter(aa);
        }

        // TEST - pulsante settings
        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openSettingsActivity= new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(openSettingsActivity);
            }
        });

        // TEST - custom ListView feed post
        //ArrayAdapter
        ListView feedListView = findViewById(R.id.feedListView);
        /*
        Post[] testPosts = new Post[20];
        for(int i=0; i<testPosts.length; i++)
            testPosts[i] = new Post("@mipmap/ic_launcher_foreground", "User " + (i+1), "Title #" + (i+1), "Topic", "#Tags of post " + (i+1), new Date(), 0, true);
        PostArrayAdapter paa = new PostArrayAdapter(this, R.layout.row_main, testPosts);
        feedListView.setAdapter(paa);
        */
        //CursorAdapter
        pdba = new PostsDBAdapter(this);
        try {
            pdba.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for(int i=0; i<10; i++)
            pdba.createPost("User " + (i+1), "Title #" + (i+1), "Topic", "#Tags of post " + (i+1), new Date(), 0, true);
        Cursor allPosts = pdba.fetchAllPosts();
        PostCursorAdapter pca = new PostCursorAdapter(this, allPosts, 1);
        feedListView.setAdapter(pca);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pdba.close();
    }

}
