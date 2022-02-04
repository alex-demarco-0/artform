package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class TopicDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "topic";
    protected static final String KEY_TOPICID = "_id"; //PK autoincrement
    protected static final String KEY_NAME = "name";

    public TopicDBAdapter(Context context) {
        this.context = context;
    }

    public TopicDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String name) {
        ContentValues values = new ContentValues();
        values.put( KEY_NAME, name );
        return values;
    }

    public long createTopic(String name) {
        ContentValues topicValues = createContentValues(name);
        return database.insertOrThrow(DATABASE_TABLE, null, topicValues);
    }

    public Cursor fetchTopicByName(String name) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_NAME },
                KEY_NAME + " = "+ name, null, null, null, null, null);
        return mCursor;
    }

    public Cursor fetchAllTopics() {
        return database.query(DATABASE_TABLE, new String[] { KEY_NAME }, null, null, null, null, null);
    }

}
