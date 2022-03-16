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

import java.util.List;

import it.artform.R;
import it.artform.TopicUserActivity;

public class TopicGridAdapter extends BaseAdapter {
    private Context context;
    private int resource = 0;
    private String[] topics = null;

    static class ViewHolder {
        TextView topicTextView;
        boolean topicChecked;
        //ImageView topicImageView; ??
    }

    public TopicGridAdapter(Context context, int resource, String[] topics) {
        this.context = context;
        this.resource = resource;
        this.topics = topics;
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
/*
    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        TopicView topicView = (view == null) ? new TopicView(context) : (TopicView) view;
        topicView.display(topics[pos], selectedPositions.contains(pos));
        return topicView;
    }
*/
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
                        List<String> topicSelection = TopicUserActivity.getTopicSelection();
                        topicSelection.remove(topics[pos]);
                        TopicUserActivity.setTopicSelection(topicSelection);
                        //TEST
                        Toast.makeText(context, topicSelection.toString(), Toast.LENGTH_SHORT).show();
                        //
                    }
                    else {
                        vh.topicTextView.setBackgroundColor(context.getColor(R.color.purple_200));
                        vh.topicChecked = true;
                        List<String> topicSelection = TopicUserActivity.getTopicSelection();
                        topicSelection.add(topics[pos]);
                        TopicUserActivity.setTopicSelection(topicSelection);
                        //TEST
                        Toast.makeText(context, topicSelection.toString(), Toast.LENGTH_SHORT).show();
                        //
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
    /*
    private Context ctx;
    private List<Topic> topics;
    private LayoutInflater layoutInflater;

    public TopicGridAdapter(Context context, List<Topic> objects){
        ctx = context;
        topics = objects;
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return null;
    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (layoutInflater == null)
//            layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if (view == null){
//            view = layoutInflater.inflate(R.layout.row_grid_item, null);
//        }
//
//        TextView topicGridName = view.findViewById(R.id.topicGridName);
//        topicGridName.setText(topics.get(i).getName());
//
//        return view;
//    }

     */
}
