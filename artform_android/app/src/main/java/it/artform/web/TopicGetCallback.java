package it.artform.web;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import it.artform.pojos.Topic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
public class TopicGetCallback implements Callback<List<Topic>> {

    private Topic topic = null;
    Context cxt = null;

    public TopicGetCallback(Context c) {
        super();
        cxt = c;
    }

    public Topic getTopic() {
        return topic;
    }

    @Override
    public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
        if (response.isSuccessful()) {
            topic = new Topic(response.body().get());
            Toast.makeText(cxt, topic.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<List<Topic>> call, Throwable t) {
        t.printStackTrace();
    }
}
-*/