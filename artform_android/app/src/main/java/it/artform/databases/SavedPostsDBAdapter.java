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
    protected static final String KEY_SAVED_POST_ID = "_id"; //PK autoincrement
    protected static final String KEY_USERID = "userId";
    protected static final String KEY_POSTID = "postId";

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

    private ContentValues createContentValues(long userId, long postId) {
        ContentValues values = new ContentValues();
        values.put( KEY_USERID, userId );
        values.put( KEY_POSTID, postId );
        return values;
    }

    public long savePost(long userId, long postId) {
        ContentValues postValues = createContentValues(userId, postId);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

    public Cursor fetchUserSavedPosts(long userId) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USERID, KEY_POSTID },
                KEY_USERID + " = " + userId, null, null, null, null, null);
        return mCursor;
    }

    public boolean deletePostFromSaved(long userId, long postId) {
        return database.delete(DATABASE_TABLE, KEY_USERID + " = " + userId + " AND " + KEY_POSTID + " = " + postId, null) > 0;
    }
}
