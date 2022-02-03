package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Date;

public class PostsDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "posts";
    protected static final String KEY_POSTID = "_id"; //PK autoincrement
    protected static final String KEY_USER = "user";
    protected static final String KEY_TITLE = "title";
    protected static final String KEY_TOPIC = "topic";
    protected static final String KEY_TAGS = "tags";
    protected static final String KEY_PUBLICATION_DATE = "publicationDate";
    protected static final String KEY_LIKE = "like";
    protected static final String KEY_TYPE = "type";

    public PostsDBAdapter(Context context) {
        this.context = context;
    }

    public PostsDBAdapter open() throws SQLException {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA Adapter: open");
        dbHelper = new ArtformDBHelper(context);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA Adapter: helper created");
        database = dbHelper.getWritableDatabase();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA Adapter: got db rw");
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String user, String title, String topic, String tags, Date publicationDate, int like, boolean type) {
        ContentValues values = new ContentValues();
        values.put( KEY_USER, user );
        values.put( KEY_TITLE, title );
        values.put( KEY_TOPIC, topic );
        values.put( KEY_TAGS, tags );
        values.put( KEY_PUBLICATION_DATE, String.valueOf(publicationDate) );
        values.put( KEY_LIKE, user );
        values.put( KEY_TYPE, type );
        return values;
    }

    public long createPost(String user, String title, String topic, String tags, Date publicationDate, int like, boolean type) {
        ContentValues postValues = createContentValues(user, title, topic, tags, publicationDate, like, type);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }
    /*
    public boolean updatePost(long postID, String user, String title, String topic, String tags, Date publicationDate, int like, boolean type) {
        ContentValues updateValues = createContentValues(user, title, topic, tags, publicationDate, like, type);
        return database.update(DATABASE_TABLE, updateValues, KEY_POSTID + "=" + notificationID, null) > 0;
    }
    */
    public boolean deletePost(long postID) {
        return database.delete(DATABASE_TABLE, KEY_POSTID + " = " + postID, null) > 0;
    }

    public Cursor fetchAllPosts() {
        return database.query(DATABASE_TABLE, new String[] { KEY_POSTID, KEY_USER, KEY_TITLE, KEY_TOPIC, KEY_TAGS, KEY_PUBLICATION_DATE, KEY_LIKE, KEY_TYPE }, null, null, null, null, null);
    }

    public Cursor fetchPostsByUser(String user) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_POSTID, KEY_USER, KEY_TITLE, KEY_TOPIC, KEY_TAGS, KEY_PUBLICATION_DATE, KEY_LIKE, KEY_TYPE },
                KEY_USER + " = " + user, null, null, null, null, null);
        return mCursor;
    }

    public void giveLike(long postID, int value) {
        database.rawQuery("UPDATE " + DATABASE_TABLE + " SET " + KEY_LIKE + " = '" + (value + 1) + "' WHERE " + KEY_POSTID + " = " + postID, null);
    }
}
