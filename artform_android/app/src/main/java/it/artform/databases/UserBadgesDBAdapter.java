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
    protected static final String KEY_USERID = "userId";
    protected static final String KEY_BADGEID = "badgeId";

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

    private ContentValues createContentValues(long userId, long badgeId) {
        ContentValues values = new ContentValues();
        values.put( KEY_USERID, userId );
        values.put( KEY_BADGEID, badgeId );
        return values;
    }

    public long earnBadge(long userId, long badgeId) {
        ContentValues postValues = createContentValues(userId, badgeId);
        return database.insertOrThrow(DATABASE_TABLE, null, postValues);
    }

    public Cursor fetchUserBadges(long userId) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USERID, KEY_BADGEID },
                KEY_USERID + " = " + userId, null, null, null, null, null);
        return mCursor;
    }

}
