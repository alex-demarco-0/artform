package it.artform.feed;
/*
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.artform.AFGlobal;
import it.artform.R;
import it.artform.UserProfileActivity;
import it.artform.pojos.Post;

public class PostGridAdapter extends ArrayAdapter<Post> {
    private Context ctx = null;
    ImageView postImageView = null;
    private List<Post> userPosts = null;

    public PostGridAdapter(Context context, List<Post> objects) {
        super();
        ctx = context;
        userPosts = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            vh.postImageView = convertView.findViewById(R.id.postImageView);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        Post p = getItem(position);
        Picasso.get().load(AFGlobal.BASE_URL + p.getContentSrc()).resize(40, 40).centerCrop().into(vh.postImageView);
        return convertView;
    }

}
*/