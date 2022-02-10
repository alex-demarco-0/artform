package it.artform;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Spinner;

public class SearchActivity extends Activity {
    SearchView simpleSearchView = null;
    Spinner topicSpinner = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        topicSpinner = findViewById(R.id.topicSpinner);

    }
}