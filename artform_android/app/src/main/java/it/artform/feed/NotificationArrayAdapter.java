package it.artform.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.artform.AFGlobal;
import it.artform.R;
import it.artform.pojos.Notification;
import it.artform.web.ArtformApiEndpointInterface;

public class NotificationArrayAdapter extends ArrayAdapter<Notification> {
    private Context ctx = null;
    private int res = 0;
    private Notification[] notificationList = null;
    ArtformApiEndpointInterface apiService = null;
    SimpleDateFormat oldFormat = null;
    SimpleDateFormat newFormat = null;

    static class ViewHolder {
        TextView descriptionTextView;
        TextView dateTextView;
    }

    public NotificationArrayAdapter(Context context, int resource, Notification[] objects) {
        super(context, resource, objects);
        ctx = context;
        res = resource;
        notificationList = objects;
        apiService = AFGlobal.getInstance().getRetrofit().create(ArtformApiEndpointInterface.class);
        //oldFormat = new SimpleDateFormat("MMM d, yyyy h:mm:ss aaa");
        oldFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        newFormat = new SimpleDateFormat("MMM d yyyy HH:mm");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
            NotificationArrayAdapter.ViewHolder vh = new NotificationArrayAdapter.ViewHolder();
            vh.descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
            vh.dateTextView = convertView.findViewById(R.id.dateTextView);
            convertView.setTag(vh);
        }
        NotificationArrayAdapter.ViewHolder vh = (NotificationArrayAdapter.ViewHolder) convertView.getTag();
        Notification n = notificationList[position];
        vh.descriptionTextView.setText(String.valueOf(n.getDescription()));
        try {
            vh.dateTextView.setText(newFormat.format(oldFormat.parse(n.getDate().toString())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //vh.dateTextView.setText(n.getDate().toString());
        //OnClickListener ?
        return convertView;
    }

}
