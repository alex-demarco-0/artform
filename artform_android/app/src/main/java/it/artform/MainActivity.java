package it.artform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         bottomNavigation;
        bottomNavigation.selectedItemId = R.id.page_2
    }

    public void pubblica(View view) {
        Intent pub = new Intent(this, ContentPubActivity.class);
        startActivity(pub);
    }
}
