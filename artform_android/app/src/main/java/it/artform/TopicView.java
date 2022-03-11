package it.artform;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TopicView extends FrameLayout {
    TextView textView;
    public TopicView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_topic_grid, this);
        textView = (TextView) getRootView().findViewById(R.id.topicGridName);
    }

    public void display(String text, boolean isSelected){
        textView.setText(text);
        display(isSelected);
    }

    private void display(boolean isSelected) {
    }

}
