package it.artform.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.artform.R;
import it.artform.pojos.Post;

public class PostArrayAdapter extends ArrayAdapter<Post> {
    private Context ctx = null;
    private int res = 0;
    private ArrayList<Post> feed = new ArrayList<>();

    static class ViewHolder {
        ImageView postImageView;
        TextView userTextView;
        TextView tagsTextView;
        TextView likeTextView;
    }

    public PostArrayAdapter(Context context, int resource, Post[] objects) {
        super(context, resource, objects);
        ctx = context;
        res = resource;
        for(int i =0; i < objects.length; i++)
            feed.add(objects[i]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
            ViewHolder vh = new ViewHolder();
            vh.postImageView = convertView.findViewById(R.id.postImageView);
            vh.userTextView = convertView.findViewById(R.id.userTextView);
            vh.tagsTextView = convertView.findViewById(R.id.tagsTextView);
            vh.likeTextView = convertView.findViewById(R.id.likeTextView);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        Post p = getItem(position);
        vh.userTextView.setText(""+p.getUser());
        vh.tagsTextView.setText(p.getTags());
        vh.likeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "You liked the post from " + vh.userTextView.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}
