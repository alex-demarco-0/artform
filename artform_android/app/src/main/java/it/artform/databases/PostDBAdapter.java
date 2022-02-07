package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Date;

public class PostDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "post";
    protected static final String KEY_POSTID = "_id"; //PK autoincrement
    protected static final String KEY_USER = "userId";
    protected static final String KEY_TITLE = "title";
    protected static final String KEY_TOPIC = "topic";
    protected static final String KEY_TAGS = "tags";
    protected static final String KEY_PUBLICATION_DATE = "publicationDate";
    protected static final String KEY_LIKE = "like";
    protected static final String KEY_TYPE = "type";

    public PostDBAdapter(Context context) {
        this.context = context;
    }

    public PostDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(long userId, String title, String topic, String tags, Date publicationDate, int like, boolean type) {
        ContentValues values = new ContentValues();
        values.put( KEY_USER, userId );
        values.put( KEY_TITLE, title );
        values.put( KEY_TOPIC, topic );
        values.put( KEY_TAGS, tags );
        values.put( KEY_PUBLICATION_DATE, String.valueOf(publicationDate) );
        values.put( KEY_LIKE, like );
        values.put( KEY_TYPE, type );
        return values;
    }

    public long createPost(long userId, String title, String topic, String tags, Date publicationDate, int like, boolean type) {
        ContentValues postValues = createContentValues(userId, title, topic, tags, publicationDate, like, type);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }
    /*
    public boolean updatePost(long postID, long userId, String title, String topic, String tags, Date publicationDate, int like, boolean type) {
        ContentValues updateValues = createContentValues(userId, title, topic, tags, publicationDate, like, type);
        return database.update(DATABASE_TABLE, updateValues, KEY_POSTID + "=" + notificationID, null) > 0;
    }
    */
    public boolean deletePost(long postId) {
        return database.delete(DATABASE_TABLE, KEY_POSTID + " = " + postId, null) > 0;
    }

    public Cursor fetchAllPosts() {
        return database.query(DATABASE_TABLE, new String[] { KEY_POSTID, KEY_USER, KEY_TITLE, KEY_TOPIC, KEY_TAGS, KEY_PUBLICATION_DATE, KEY_LIKE, KEY_TYPE }, null, null, null, null, null);
    }

    public Cursor fetchUserPosts(long userId) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_POSTID, KEY_USER, KEY_TITLE, KEY_TOPIC, KEY_TAGS, KEY_PUBLICATION_DATE, KEY_LIKE, KEY_TYPE },
                KEY_USER + " = " + userId, null, null, null, null, null);
        return mCursor;
    }

    public void giveLike(long postId, int current) {
        database.rawQuery("UPDATE " + DATABASE_TABLE + " SET " + KEY_LIKE + " = '" + (current + 1) + "' WHERE " + KEY_POSTID + " = " + postId, null);
    }
}
