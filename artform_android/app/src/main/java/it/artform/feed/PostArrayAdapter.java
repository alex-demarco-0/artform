package it.artform.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import it.artform.AFGlobal;
import it.artform.R;
import it.artform.pojos.Post;

public class PostArrayAdapter extends ArrayAdapter<Post> {
    private Context ctx = null;
    private int res = 0;
    private Post[] postList = null;

    static class ViewHolder {
        ImageView postImageView;
        TextView titleTextView;
        TextView userTextView;
        TextView topicTextView;
        TextView tagsTextView;
        TextView likeTextView;
    }

    public PostArrayAdapter(Context context, int resource, Post[] objects) {
        super(context, resource, objects);
        ctx = context;
        res = resource;
        postList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
            ViewHolder vh = new ViewHolder();
            vh.postImageView = convertView.findViewById(R.id.postImageView);
            vh.titleTextView = convertView.findViewById(R.id.titleTextView);
            vh.userTextView = convertView.findViewById(R.id.userTextView);
            vh.topicTextView = convertView.findViewById(R.id.topicTextView);
            vh.tagsTextView = convertView.findViewById(R.id.tagsTextView);
            vh.likeTextView = convertView.findViewById(R.id.likeTextView);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        Post p = postList[position];
        //Toast.makeText(ctx, p.toString(), Toast.LENGTH_LONG).show();
        vh.userTextView.setText(String.valueOf(p.getUser()));
        vh.titleTextView.setText(String.valueOf(p.getTitle()));
        vh.topicTextView.setText(String.valueOf(p.getTopic()));
        vh.tagsTextView.setText(String.valueOf(p.getTags()));
        String postImgUri = AFGlobal.POST_IMAGE_PATH + p.getContentSrc();
        Picasso.get().load(postImgUri).resize(900, 0).into(vh.postImageView);
        vh.likeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //implementare incemento di like
                Toast.makeText(ctx, "You liked the post from " + vh.userTextView.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}
