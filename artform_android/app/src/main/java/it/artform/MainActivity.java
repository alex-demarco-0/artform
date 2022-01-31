package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import it.artform.post.Post;
import it.artform.post.PostArrayAdapter;

public class MainActivity extends Activity {

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

        // custom ListView feed post
        ListView feedListView = findViewById(R.id.feedListView);
        Post[] testPosts = new Post[20];
        for(int i=0; i<testPosts.length; i++)
            testPosts[i] = new Post(null, "User " + (i+1), "#Tags of post " + (i+1));
        PostArrayAdapter paa = new PostArrayAdapter(this, R.layout.row_main, testPosts);
        feedListView.setAdapter(paa);
    }
/*
    public void pubblica(View view) {
        Intent pub = new Intent(this, ContentPubActivity.class);
        startActivity(pub);
    }
*/
}
