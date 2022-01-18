package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*
        ListView datiRegistrazioneListView = findViewById(R.id.datiRegistrazioneListView);

        Bundle datiRegistrazione = getIntent().getExtras();
        String[] listaDatiRegistrazione = new String[datiRegistrazione.size()];
        int i = 0;
        for(String key: datiRegistrazione.keySet()) {
            if(datiRegistrazione.get(key) != null)
                listaDatiRegistrazione[i] = datiRegistrazione.get(key).toString();
            i++;
        }
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDatiRegistrazione);
        datiRegistrazioneListView.setAdapter(aa);
    }

    public void pubblica(View view) {
        Intent pub = new Intent(this, ContentPubActivity.class);
        startActivity(pub);
    }
    */

    }
}
