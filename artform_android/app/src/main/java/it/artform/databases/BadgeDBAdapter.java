package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class BadgeDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "badge";
    protected static final String KEY_BADGEID = "_id"; //PK autoincrement
    protected static final String KEY_NAME = "name";
    protected static final String KEY_DESCRIPTION = "description";
    protected static final String KEY_POINTS = "points";

    public BadgeDBAdapter(Context context) {
        this.context = context;
    }

    public BadgeDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String name, String description, int points , long userId) {
        ContentValues values = new ContentValues();
        values.put( KEY_NAME, name );
        values.put( KEY_DESCRIPTION, description );
        values.put( KEY_POINTS, points );
        return values;
    }

    public long createBadge(String name, String description, int points , long userId) {
        ContentValues postValues = createContentValues(name, description, points, userId);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

}
