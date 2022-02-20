package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class UserTopicsDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "userTopics";
    protected static final String KEY_USER_TOPICS_ID = "_id"; // PK autoincrement
    protected static final String KEY_USER_USERNAME = "userUsername";
    protected static final String KEY_TOPIC_NAME = "topicName";

    public UserTopicsDBAdapter(Context context) {
        this.context = context;
    }

    public UserTopicsDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String userUsername, String topicName) {
        ContentValues values = new ContentValues();
        values.put( KEY_USER_USERNAME, userUsername );
        values.put( KEY_TOPIC_NAME, topicName );
        return values;
    }

    public long selectTopic(String userUsername, String topicName) {
        ContentValues postValues = createContentValues(userUsername, topicName);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

    public Cursor fetchUserTopics(String userUsername) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USER_USERNAME, KEY_TOPIC_NAME },
                KEY_USER_USERNAME + " = " + userUsername, null, null, null, null, null);
        return mCursor;
    }

    public boolean deleteTopicFromSelected(String userUsername, String topicName) {
        return database.delete(DATABASE_TABLE, KEY_USER_USERNAME + " = " + userUsername + " AND " + KEY_TOPIC_NAME + " = " + topicName, null) > 0;
    }

}

