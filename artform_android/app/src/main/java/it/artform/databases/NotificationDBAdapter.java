package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Date;

public class NotificationDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "notification";
    protected static final String KEY_NOTIFICATIONID = "_id"; //PK autoincrement
    protected static final String KEY_DATE = "date";
    protected static final String KEY_CATEGORY = "category";
    protected static final String KEY_DESCRIPTION = "description";
    protected static final String KEY_LINK = "link";
    protected static final String KEY_USER = "userId";

    public NotificationDBAdapter(Context context) {
        this.context = context;
    }

    public NotificationDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(Date date, int category, String description, String link, long userId) {
        ContentValues values = new ContentValues();
        values.put( KEY_DATE, String.valueOf(date));
        values.put( KEY_CATEGORY, category );
        values.put( KEY_DESCRIPTION, description );
        values.put( KEY_LINK, String.valueOf(link));
        values.put( KEY_USER, userId );
        return values;
    }

    public long createNotification(Date date, int category, String description, String link, long userId) {
        ContentValues notificationValues = createContentValues(date, category, description, link, userId);
        return database.insertOrThrow(DATABASE_TABLE, null, notificationValues);
    }
/*
    public boolean updateNotification(long notificationID, Date date, int category, String description, String link, long userId) {
        ContentValues updateValues = createContentValues(date, category, description, link, userId);
        return database.update(DATABASE_TABLE, updateValues, KEY_NOTIFICATIONID + "=" + notificationID, null) > 0;
    }
*/
    public boolean deleteNotification(long userId) {
        return database.delete(DATABASE_TABLE, KEY_USER + "=" + userId, null) > 0;
    }

    public Cursor fetchAllNotifications() {
        return database.query(DATABASE_TABLE, new String[] { KEY_NOTIFICATIONID, KEY_DATE, KEY_CATEGORY, KEY_DESCRIPTION, KEY_LINK, KEY_USER }, null, null, null, null, null);
    }

    public Cursor fetchNotificationsByCategory(String category) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_NOTIFICATIONID, KEY_DATE, KEY_CATEGORY, KEY_DESCRIPTION, KEY_LINK, KEY_USER },
                KEY_CATEGORY + " = " + category, null, null, null, null, null);
        return mCursor;
    }

}
