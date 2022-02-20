package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class UserNotificationsDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "userNotifications";
    protected static final String KEY_USER_NOTIFICATIONS_ID = "_id"; //PK autoincrement
    protected static final String KEY_USER_USERNAME = "userUsername"; // server DB PK
    protected static final String KEY_USER_EXT_USERNAME = "userExtUsername"; // server DB PK

    public UserNotificationsDBAdapter(Context context) {
        this.context = context;
    }

    public UserNotificationsDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String userUsername, String userExtUsername) {
        ContentValues values = new ContentValues();
        values.put( KEY_USER_USERNAME, userUsername );
        values.put( KEY_USER_EXT_USERNAME, userExtUsername );
        return values;
    }

    public long activateUserNotifications(String userUsername, String userExtUsername) {
        ContentValues postValues = createContentValues(userUsername, userExtUsername);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

    public Cursor fetchUserActiveNotifications(String userUsername) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USER_USERNAME, KEY_USER_EXT_USERNAME },
                KEY_USER_USERNAME + " = " + userUsername, null, null, null, null, null);
        return mCursor;
    }

    public boolean deactivateUserNotifications(String userUsername, String userExtUsername) {
        return database.delete(DATABASE_TABLE, KEY_USER_USERNAME + " = " + userUsername + " AND " + KEY_USER_EXT_USERNAME + " = " + userExtUsername, null) > 0;
    }

}
