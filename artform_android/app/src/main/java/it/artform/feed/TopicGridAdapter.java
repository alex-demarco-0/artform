package it.artform.feed;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import it.artform.R;

public class TopicGridAdapter extends BaseAdapter {
    private Context context;
    private int resource = 0;
    private String[] topics = null;
    List<String> topicSelection = null;

    static class ViewHolder {
        TextView topicTextView;
        boolean topicChecked;
        //ImageView topicImageView; ??
    }

    public TopicGridAdapter(Context context, int resource, String[] topics) {
        this.context = context;
        this.resource = resource;
        this.topics = topics;
        topicSelection = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return topics.length;
    }

    @Override
    public Object getItem(int i) {
        return topics[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<String> getTopicSelection() {
        return topicSelection;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
            ViewHolder vh = new ViewHolder();
            vh.topicTextView = convertView.findViewById(R.id.topicTextView);
            vh.topicChecked = false;
            vh.topicTextView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    if(vh.topicChecked) {
                        vh.topicTextView.setBackgroundColor(context.getColor(R.color.white));
                        vh.topicChecked = false;
                        topicSelection.remove(topics[pos]);
                        /*TEST
                        Toast.makeText(context, topicSelection.toString(), Toast.LENGTH_SHORT).show();
                        */
                    }
                    else {
                        vh.topicTextView.setBackgroundColor(context.getColor(R.color.purple_200));
                        vh.topicChecked = true;
                        topicSelection.add(topics[pos]);
                        /*TEST
                        Toast.makeText(context, topicSelection.toString(), Toast.LENGTH_SHORT).show();
                        */
                    }
                }
            });
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        String topicName = topics[pos];
        vh.topicTextView.setText(topicName);
        return convertView;
    }

}
