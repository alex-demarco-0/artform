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
    protected static final String KEY_USERID = "userId";
    protected static final String KEY_USER_EXT_ID = "userExtId";

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

    private ContentValues createContentValues(long userId, long userExtId) {
        ContentValues values = new ContentValues();
        values.put( KEY_USERID, userId );
        values.put( KEY_USER_EXT_ID, userExtId );
        return values;
    }

    public long activateUserNotifications(long userId, long userExtId) {
        ContentValues postValues = createContentValues(userId, userExtId);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

    public Cursor fetchUserActiveNotifications(long userId) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USERID, KEY_USER_EXT_ID },
                KEY_USERID + " = " + userId, null, null, null, null, null);
        return mCursor;
    }

    public boolean deactivateUserNotifications(long userId, long userExtId) {
        return database.delete(DATABASE_TABLE, KEY_USERID + " = " + userId + " AND " + KEY_USER_EXT_ID + " = " + userExtId, null) > 0;
    }

}
