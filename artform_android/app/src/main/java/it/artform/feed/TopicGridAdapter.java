package it.artform.feed;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import it.artform.TopicView;

public class TopicGridAdapter extends BaseAdapter {

    private Activity activity;
    private int resource;
    private String[] topicStrig;
    public  List selectedPositions;

    public TopicGridAdapter(Activity activity, String[] topicStrig) {
        this.activity = activity;
        this.topicStrig = topicStrig;
        selectedPositions = new ArrayList<>();
    }
    public TopicGridAdapter(Activity activity,int resource, String[] topicStrig) {
        this.activity = activity;
        this.resource = resource;
        this.topicStrig = topicStrig;
        selectedPositions = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return topicStrig.length;
    }

    @Override
    public Object getItem(int i) {
        return topicStrig[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TopicView topicView = (view == null) ? new TopicView(activity) : (TopicView) view;
        topicView.display(topicStrig[i], selectedPositions.contains(i));
        return topicView;
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
