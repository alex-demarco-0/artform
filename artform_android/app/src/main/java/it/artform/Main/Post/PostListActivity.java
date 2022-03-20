package it.artform.Main.Post;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import it.artform.R;
import it.artform.feed.PostArrayAdapter;
import it.artform.pojos.Post;

public class PostListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        ListView postListView = findViewById(R.id.postListView);
        Bundle bundle = getIntent().getExtras();
        postListView.setAdapter(new PostArrayAdapter(this, R.layout.row_post_list, (Post[]) bundle.getSerializable("postList")));
        if(bundle.get("postIndex") != null)
            postListView.setSelection((Integer) bundle.get("postIndex"));
    }
}