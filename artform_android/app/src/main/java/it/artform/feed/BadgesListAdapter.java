package it.artform.feed;
/*
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import it.artform.pojos.Badge;

public class BadgesListAdapter extends RecyclerView.Adapter {
    Context context = null;
    public Badge[] badgeList = null;

    public BadgesListAdapter(Context context, Badge[] objects) {
        this.context = context;
        badgeList = objects;
    }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
       ImageView badgeImageView = null;
       if (convertView == null) {
           badgeImageView = new ImageView(context);
           badgeImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       }
       else
           badgeImageView = (ImageView) convertView;
       Badge b = badgeList[position];
       String badgeResUri = "http://172.24.128.1:8080/media/badgesBmp/" + b.getName() + ".png";
       Picasso.get().load(badgeResUri).into(badgeImageView);
       return badgeImageView;
   }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
*/