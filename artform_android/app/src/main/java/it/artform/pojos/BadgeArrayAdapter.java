package it.artform.pojos;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import it.artform.Badge;

public class BadgeArrayAdapter extends ArrayAdapter<Badge> {
    Context context;
    int res=0;
    public ArrayAdapter<Badge> badges;

    public BadgeArrayAdapter(@NonNull Context context, int resource, @NonNull Badge[] objects) {
        super(context, resource, objects);
        this.context=context;
        res=resource;
        for(int i=0;i<objects.length; i++){
            badges.add(objects[i]);
        }
   }
}
