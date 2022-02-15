package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class UserBadgesDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "userBadges";
    protected static final String KEY_USER_BADGES_ID = "_id"; //PK autoincrement
    protected static final String KEY_USER_USERNAME = "userUsername";
    protected static final String KEY_BADGE_NAME = "badgeName";

    public UserBadgesDBAdapter(Context context) {
        this.context = context;
    }

    public UserBadgesDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String userUsername, String badgeName) {
        ContentValues values = new ContentValues();
        values.put( KEY_USER_USERNAME, userUsername );
        values.put( KEY_BADGE_NAME, badgeName );
        return values;
    }

    public long earnBadge(String userUsername, String badgeName) {
        ContentValues postValues = createContentValues(userUsername, badgeName);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

    public Cursor fetchUserBadges(String userUsername) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USER_USERNAME, KEY_BADGE_NAME },
                KEY_USER_USERNAME + " = " + userUsername, null, null, null, null, null);
        return mCursor;
    }

}
