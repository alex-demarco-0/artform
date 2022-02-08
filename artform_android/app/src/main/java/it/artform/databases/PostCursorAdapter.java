package it.artform.databases;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import it.artform.R;

public class PostCursorAdapter extends CursorAdapter {

    public PostCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View retView = inflater.inflate(R.layout.row_main, viewGroup, false);
        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView postImageView = (ImageView) view.findViewById(R.id.postImageView) ;
        TextView userTextView = (TextView) view.findViewById(R.id.userTextView);
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView tagsTextView = (TextView) view.findViewById(R.id.tagsTextView);
        TextView likeTextView = (TextView) view.findViewById(R.id.likeTextView);
        postImageView.setImageResource(R.mipmap.ic_launcher_foreground);
        int userCol = cursor.getColumnIndex(PostDBAdapter.KEY_USER);
        userTextView.setText("User #" + cursor.getString(userCol));
        int titleCol = cursor.getColumnIndex(PostDBAdapter.KEY_TITLE);
        titleTextView.setText(cursor.getString(titleCol));
        int tagsCol = cursor.getColumnIndex(PostDBAdapter.KEY_TAGS);
        tagsTextView.setText(cursor.getString(tagsCol));
        likeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You liked the post from " + cursor.getString(userCol), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
