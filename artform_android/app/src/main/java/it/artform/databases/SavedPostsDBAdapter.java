package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class SavedPostsDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "savedPosts";
    protected static final String KEY_SAVED_POST_ID = "_id"; // PK autoincrement
    protected static final String KEY_USER_USERNAME = "userUsername"; // server DB PK
    protected static final String KEY_POST_ID = "postId"; // server DB PK

    public SavedPostsDBAdapter(Context context) {
        this.context = context;
    }

    public SavedPostsDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String userUsername, int postId) {
        ContentValues values = new ContentValues();
        values.put( KEY_USER_USERNAME, userUsername );
        values.put( KEY_POST_ID, postId );
        return values;
    }

    public long savePost(String userUsername, int postId) {
        ContentValues postValues = createContentValues(userUsername, postId);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

    public Cursor fetchUserSavedPosts(String userUsername) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USER_USERNAME, KEY_POST_ID },
                KEY_USER_USERNAME + " = " + userUsername, null, null, null, null, null);
        return mCursor;
    }

    public boolean deletePostFromSaved(String userUsername, int postId) {
        return database.delete(DATABASE_TABLE, KEY_USER_USERNAME + " = " + userUsername + " AND " + KEY_POST_ID + " = " + postId, null) > 0;
    }
}
