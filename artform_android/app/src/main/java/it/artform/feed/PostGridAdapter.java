package it.artform.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

public class PostGridAdapter extends BaseAdapter {
    private Context ctx = null;
    private List<Post> userPosts = null;

    public PostGridAdapter(Context context, List<Post> objects) {
        ctx = context;
        userPosts = objects;
    }

    @Override
    public int getCount() {
        return userPosts.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView postImageView = null;
        if (convertView == null) {
            postImageView = new ImageView(ctx);
            postImageView.setLayoutParams(new GridView.LayoutParams(270, 270));
            postImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //postImageView.setPadding(16, 16, 16, 16);
        }
        else
            postImageView = (ImageView) convertView;
        Post p = userPosts.get(position);
        Picasso.get().load(AFGlobal.BASE_URL + p.getContentSrc()).into(postImageView);
        return postImageView;
    }

}
