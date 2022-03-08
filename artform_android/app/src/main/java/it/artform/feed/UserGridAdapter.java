package it.artform.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.artform.AFGlobal;
import it.artform.R;

public class UserGridAdapter extends BaseAdapter {
    private Context ctx = null;
    private int res = 0;
    private String[] users = null;

    static class ViewHolder {
        ImageView profilePicImageView;
        TextView usernameTextView;
    }

    public UserGridAdapter(Context context, int resource, String[] objects) {
        ctx = context;
        res = resource;
        users = objects;
    }

    @Override
    public int getCount() {
        return users.length;
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
            ViewHolder vh = new ViewHolder();
            vh.profilePicImageView = convertView.findViewById(R.id.profilePicImageView);
            //vh.profilePicImageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            vh.profilePicImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            vh.usernameTextView = convertView.findViewById(R.id.usernameTextView);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        String username = users[position];
        vh.usernameTextView.setText(username);
        String profilePicResUri = AFGlobal.USER_PROPIC_PATH + username + ".jpg";
        Picasso.get().load(profilePicResUri).into(vh.profilePicImageView);
        return convertView;
    }

}
