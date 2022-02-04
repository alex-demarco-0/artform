package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class UserDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "users";
    protected static final String KEY_USERID = "_id"; //PK autoincrement
    protected static final String KEY_NAME = "name";
    protected static final String KEY_SURNAME = "surname";
    protected static final String KEY_USERNAME = "username";
    protected static final String KEY_EMAIL = "email";
    protected static final String KEY_PHONE = "phone";
    protected static final String KEY_PASSWORD = "password";
    protected static final String KEY_POINTS = "points";

    public UserDBAdapter(Context context) {
        this.context = context;
    }

    public UserDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String name, String surname, String username, String email, String phone, String password, int points) {
        ContentValues values = new ContentValues();
        values.put( KEY_NAME, name );
        values.put( KEY_SURNAME, name );
        values.put( KEY_USERNAME, name );
        values.put( KEY_EMAIL, name );
        values.put( KEY_PHONE, name );
        values.put( KEY_PASSWORD, name );
        values.put( KEY_POINTS, name );
        return values;
    }

    public long createUser(String name, String surname, String username, String email, String phone, String password, int points) {
        ContentValues userValues = createContentValues(name, surname, username, email, phone, password, points);
        return database.insertOrThrow(DATABASE_TABLE, null, userValues);
    }

    public boolean updateUser(long userID, String name, String surname, String username, String email, String phone, String password, int points) {
        ContentValues updateValues = createContentValues(name, surname, username, email, phone, password, points);
        return database.update(DATABASE_TABLE, updateValues, KEY_USERID + "=" + userID, null) > 0;
    }

    public boolean deleteUser(long userID) {
        return database.delete(DATABASE_TABLE, KEY_USERID + "=" + userID, null) > 0;
    }

    public Cursor fetchAllUsers() {
        return database.query(DATABASE_TABLE, new String[] { KEY_USERID, KEY_NAME, KEY_SURNAME, KEY_USERNAME, KEY_EMAIL, KEY_PHONE, KEY_PASSWORD, KEY_POINTS }, null, null, null, null, null);
    }

    public Cursor fetchUsersByFilter(String filter) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USERID, KEY_NAME, KEY_SURNAME, KEY_USERNAME, KEY_EMAIL, KEY_PHONE, KEY_PASSWORD, KEY_POINTS },
                KEY_USERNAME + " like '%"+ filter + "%'", null, null, null, null, null);
        return mCursor;
    }

}
