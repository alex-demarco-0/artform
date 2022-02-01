package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class UsersDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "";
//keys

    public UsersDBAdapter(Context context) {
        this.context = context;
    }

    public UsersDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String url) {
        ContentValues values = new ContentValues();
        //values.put( KEY_URL, url );
        return values;
    }
    /*
    public long createUser() {
        ContentValues initialValues = createContentValues();
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    public boolean updateSite(long userID) {
        ContentValues updateValues = createContentValues();
        return database.update(DATABASE_TABLE, updateValues, /*KEY_SITEID + "=" + siteID, null) > 0;
    }

    public boolean deleteUser(long contactID) {
        return database.delete(DATABASE_TABLE, /*KEY_SITEID + "=" + contactID, null) > 0;
    }

    public Cursor fetchAllUsers() {
        return database.query(DATABASE_TABLE, new String[] { KEY_SITEID, KEY_URL }, null, null, null, null, null);
    }

    public Cursor fetchUsersByFilter(String filter) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_SITEID, KEY_URL },
                KEY_URL + " like '%"+ filter + "%'", null, null, null, null, null);
        return mCursor;
    }
    */
}
