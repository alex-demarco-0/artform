package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import it.artform.pojos.User;

public class UserDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "user";
    protected static final String KEY_USERID = "_id"; // PK autoincrement
    protected static final String KEY_NAME = "name";
    protected static final String KEY_SURNAME = "surname";
    protected static final String KEY_USERNAME = "username"; // server DB PK
    protected static final String KEY_EMAIL = "email";
    protected static final String KEY_PHONE = "phone";
    protected static final String KEY_PASSWORD = "password";
    protected static final String KEY_BIO = "bio";
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

    private ContentValues createContentValues(String name, String surname, String username, String email, String phone, String password, String bio, int points) {
        ContentValues values = new ContentValues();
        values.put( KEY_NAME, name );
        values.put( KEY_SURNAME, surname );
        values.put( KEY_USERNAME, username );
        values.put( KEY_EMAIL, email );
        values.put( KEY_PHONE, phone );
        values.put( KEY_PASSWORD, password );
        values.put( KEY_BIO, bio );
        values.put( KEY_POINTS, points );
        return values;
    }

    public long createUser(User u) {
        ContentValues userValues = createContentValues(u.getName(), u.getSurname(), u.getUsername(), u.getEmail(), u.getPhone(), u.getPassword(), u.getBio(), u.getPoints());
        return database.insertOrThrow(DATABASE_TABLE, null, userValues);
    }

    public boolean updateUser(String username, User u) {
        ContentValues updateValues = createContentValues(u.getName(), u.getSurname(), u.getUsername(), u.getEmail(), u.getPhone(), u.getPassword(), u.getBio(), u.getPoints());
        return database.update(DATABASE_TABLE, updateValues, KEY_USERNAME + "=" + username, null) > 0;
    }

    public boolean deleteUser(String username) {
        return database.delete(DATABASE_TABLE, KEY_USERNAME + "=" + username, null) > 0;
    }

    public Cursor fetchAllUsers() {
        return database.query(DATABASE_TABLE, new String[] { KEY_USERID, KEY_NAME, KEY_SURNAME, KEY_USERNAME, KEY_EMAIL, KEY_PHONE, KEY_PASSWORD, KEY_BIO, KEY_POINTS }, null, null, null, null, null);
    }

    public Cursor fetchUsersByFilter(String filter) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_USERID, KEY_NAME, KEY_SURNAME, KEY_USERNAME, KEY_EMAIL, KEY_PHONE, KEY_PASSWORD, KEY_BIO, KEY_POINTS },
                KEY_USERNAME + " like '%"+ filter + "%'", null, null, null, null, null);
        return mCursor;
    }

    public void givePoints(String username, int current, int points) {
        database.rawQuery("UPDATE " + DATABASE_TABLE + " SET " + KEY_POINTS + " = '" + (current + points) + "' WHERE " + KEY_USERNAME + " = " + username, null);
    }

}
