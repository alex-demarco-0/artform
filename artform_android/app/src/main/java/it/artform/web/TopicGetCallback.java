package it.artform.web;

import android.content.Context;
import android.widget.Toast;

import it.artform.pojos.Topic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicGetCallback implements Callback<Topic> {
    private Topic topic = null;
    Context cxt = null;

    public TopicGetCallback(Context c) {
        super();
        cxt = c;
    }

    @Override
    public void onResponse(Call<Topic> call, Response<Topic> response) {
        if (response.isSuccessful()) {
            topic = new Topic(response.body().getName());
            Toast.makeText(cxt, topic.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Topic> call, Throwable t) {
        t.printStackTrace();
    }

    public Topic getTopic() {
        return topic;
    }
}
