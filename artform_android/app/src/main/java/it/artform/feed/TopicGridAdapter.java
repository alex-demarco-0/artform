package it.artform.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import it.artform.R;

public class TopicGridAdapter extends BaseAdapter {

    private Context context;
    private int resource = 0;
    private String[] topics = null;

    static class ViewHolder {
        CheckedTextView topicTextView;
        //ImageView topicImageView; ??
    }

    public TopicGridAdapter(Context context, String[] topics) {
        this.context = context;
        this.topics = topics;
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
            vh.topicTextView = convertView.findViewById(R.id.topicCheckedTextView);
            convertView.setTag(vh);
        }
        UserGridAdapter.ViewHolder vh = (UserGridAdapter.ViewHolder) convertView.getTag();
        String topicName = topics[pos];
        vh.usernameTextView.setText(topicName);
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
