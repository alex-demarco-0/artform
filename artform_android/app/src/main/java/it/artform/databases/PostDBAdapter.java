package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Date;

import it.artform.pojos.Post;

public class PostDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "post";
    protected static final String KEY_POSTID = "_id"; // PK autoincrement
    protected static final String KEY_EXTERNAL_ID = "id"; // server DB PK
    protected static final String KEY_USER = "userUsername";
    protected static final String KEY_TITLE = "title";
    protected static final String KEY_TOPIC = "topic";
    protected static final String KEY_TAGS = "tags";
    protected static final String KEY_PUBLICATION_DATE = "publicationDate";
    protected static final String KEY_LIKE = "like";
    protected static final String KEY_TYPE = "type";
    protected static final String KEY_CONTENT = "contentSrc";

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

    private ContentValues createContentValues(int id, String userUsername, String title, String topic, String[] tags, Date publicationDate, int like, boolean type, String contentSrc) {
        ContentValues values = new ContentValues();
        values.put( KEY_EXTERNAL_ID, id );
        values.put( KEY_USER, userUsername );
        values.put( KEY_TITLE, title );
        values.put( KEY_TOPIC, topic );
        String tagsStr = "";
        for(String tag: tags)
            tagsStr += (", " + tag);
        values.put( KEY_TAGS, tagsStr );
        values.put( KEY_PUBLICATION_DATE, String.valueOf(publicationDate) );
        values.put( KEY_LIKE, like );
        values.put( KEY_TYPE, type );
        values.put( KEY_CONTENT, contentSrc );
        return values;
    }

    public long createPost(Post p) {
        ContentValues postValues = createContentValues(p.getId(), p.getUser(), p.getTitle(), p.getTopic(), p.getTags(), p.getPublicationDate(), p.getLike(), p.getType(), p.getContentSrc());
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

    public boolean updatePost(int id, Post p) {
        ContentValues updateValues = createContentValues(p.getId(), p.getUser(), p.getTitle(), p.getTopic(), p.getTags(), p.getPublicationDate(), p.getLike(), p.getType(), p.getContentSrc());
        return database.update(DATABASE_TABLE, updateValues, KEY_EXTERNAL_ID + "=" + id, null) > 0;
    }

    public boolean deletePost(int id) {
        return database.delete(DATABASE_TABLE, KEY_EXTERNAL_ID + " = " + id, null) > 0;
    }

    public Cursor fetchAllPosts() {
        return database.query(DATABASE_TABLE, new String[] { KEY_POSTID, KEY_EXTERNAL_ID, KEY_USER, KEY_TITLE, KEY_TOPIC, KEY_TAGS, KEY_PUBLICATION_DATE, KEY_LIKE, KEY_TYPE, KEY_CONTENT }, null, null, null, null, null);
    }

    public Cursor fetchUserPosts(String username) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_POSTID, KEY_EXTERNAL_ID, KEY_USER, KEY_TITLE, KEY_TOPIC, KEY_TAGS, KEY_PUBLICATION_DATE, KEY_LIKE, KEY_TYPE, KEY_CONTENT },
                KEY_USER + " = " + username, null, null, null, null, null);
        return mCursor;
    }

    public void giveLike(long postId, int current) {
        database.rawQuery("UPDATE " + DATABASE_TABLE + " SET " + KEY_LIKE + " = '" + (current + 1) + "' WHERE " + KEY_POSTID + " = " + postId, null);
    }
}
