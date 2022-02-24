package it.artform;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

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
        postListView.smoothScrollToPosition((Integer) bundle.get("postIndex"));
    }
}