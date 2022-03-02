package it.artform.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import it.artform.R;
import it.artform.pojos.Badge;

public class BadgeListAdapter extends RecyclerView.Adapter {
    private Badge[] badgeList = null;

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView badgeImageView;

        public ViewHolder(View view) {
            super(view);
            badgeImageView = view.findViewById(R.id.badgeImageView);
            badgeImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        public void bindData(Badge badge) {
            String badgeResUri = "http://172.24.128.1:8080/media/badgesBmp/" + badge.getName() + ".png";
            Picasso.get().load(badgeResUri).into(badgeImageView);
        }
    }

    public BadgeListAdapter(Context context, Badge[] objects) {
        badgeList = objects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(badgeList[position]);
    }

    @Override
    public int getItemCount() {
        return badgeList.length;
    }
}
