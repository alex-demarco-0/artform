package it.artform;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import it.artform.feed.TopicGridAdapter;
import it.artform.pojos.Topic;
import it.artform.web.ArtformApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicUserActivity extends Activity {
    // web services declaration
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;

    // widgets declaration
    GridView topicsGridView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_user);

        // widget setup
        topicsGridView = findViewById(R.id.topicsGridView);

        //ricevere parametro User da RegisterActivity

        // web services setup
        app = (AFGlobal) getApplication();
        apiService = app.retrofit.create(ArtformApiEndpointInterface.class);

        // GET Topics
        fetchTopics();

        //pulsante effettua registrazione
        //e commit dei topic selezionati
    }

    private void fetchTopics() {
        Call<List<Topic>> getTopicCall = apiService.getAllTopics();
        getTopicCall.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()){
                    String[] topics = new String[response.body().size()];
                    for (int i = 0; i < topics.length; i++)
                        topics[i] = response.body().get(i).getName();
                    topicsGridView.setAdapter(new TopicGridAdapter(TopicUserActivity.this, R.layout.item_topic_grid, topics));
                    topicsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            //if not selected
                            selectTopic(position); //inserendo in una lista (di Call) la coda di Topic da aggiungere
                                                  //evidenziare
                            //if already selected
                            //deselectTopic() //togliendo dalla lista
                                             //togliere evidenziazione
                        }
                    });
                } else
                    Toast.makeText(TopicUserActivity.this, "Error while fetching topics: ERROR" + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Toast.makeText(TopicUserActivity.this, "Error while fetching topics: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void selectTopic(int position) {
        // TEST
        Toast.makeText(this, "Hai Premuto " + position, Toast.LENGTH_SHORT).show();
        //
        // Call<String> userSelectsTopic
    }
}
