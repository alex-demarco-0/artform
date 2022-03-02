 package it.artform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.artform.feed.TopicGridAdapter;
import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicSelectActivity extends Activity {
    /*
    //api
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;

    // GRIDVIEW
    GridView topicGridView;
    private ArrayList<String> topicResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        topicGridView = findViewById(R.id.topicGridView);



        Call<List<Topic>> getTopicsCall = apiService.getAllTopics();
        getTopicsCall.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()) {
                    String[] topics



//                    String[] topics = new String[response.body().size()];
//                    for (int i = 0; i < topics.length; i++)
//                        topics[i] = response.body().get(i-1).getName();
//                    if (topics.length > 0) {
//                        TopicGridAdapter topicGridAdapter = new TopicGridAdapter(TopicSelectActivity.this, topicResponseList);
//                        topicGridView.setOnItemClickListener(new GridView().setMultiChoiceModeListener();) {
//
//                        });
//                        topicGridView.setAdapter(topicGridAdapter);
//                    }

                    /*
                    topics = new Topic[response.body().size()];
                    for (int i = 0; i < topics.length; i++)
                        topics[i] = response.body().get(i);

                    if (topics.length > 0) {
                        topicGridView.setAdapter(new TopicGridAdapter(TopicSelectActivity.this, topics));
                        topicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                openMainActivity(position);
                            }
                        });
                    }
                    */
    /*
                }
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(TopicSelectActivity.this, "Si è verificato un problema durante la richiesta " + t, Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
//
//    private void openMainActivity(int position) {
//        Intent homeIntent = new Intent(TopicSelectActivity.this, MainActivity.class);
//        homeIntent.putExtra("topicList", topics);
//        homeIntent.putExtra("topicIndex", position);
//        startActivity(homeIntent);
//    }
*/
}

