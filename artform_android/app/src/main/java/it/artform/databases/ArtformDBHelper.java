package it.artform.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArtformDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "artform.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_USERS = "CREATE TABLE IF NOT EXISTS " +
            UsersDBAdapter.DATABASE_TABLE +
            " (_id integer PRIMARY KEY AUTOINCREMENT, " +
            UsersDBAdapter.KEY_NAME + " varchar(50) NOT NULL, " +
            UsersDBAdapter.KEY_SURNAME + " varchar(50) NOT NULL, " +
            UsersDBAdapter.KEY_USERNAME + " varchar(50) NOT NULL, " +
            UsersDBAdapter.KEY_EMAIL + " varchar(50) NOT NULL," +
            UsersDBAdapter.KEY_PHONE + " varchar(10) DEFAULT NULL, " +
            UsersDBAdapter.KEY_PASSWORD + " varchar(50) NOT NULL, " +
            UsersDBAdapter.KEY_POINTS + " integer NOT NULL DEFAULT '0'); ";
    private static final String CREATE_POSTS = "CREATE TABLE IF NOT EXISTS " +
            PostsDBAdapter.DATABASE_TABLE +
            " (_id integer PRIMARY KEY AUTOINCREMENT, " +
            PostsDBAdapter.KEY_USER + " varchar(50) NOT NULL, " +
            PostsDBAdapter.KEY_TOPIC + " varchar(50) NOT NULL, " +
            PostsDBAdapter.KEY_TAGS + " varchar(100) NOT NULL, " +
            PostsDBAdapter.KEY_PUBLICATION_DATE + " timestamp NOT NULL, " +
            PostsDBAdapter.KEY_LIKE + " integer NOT NULL DEFAULT '0', " +
            PostsDBAdapter.KEY_TYPE + " boolean NOT NULL, " +
            "); ";
    private static final String CREATE_NOTIFICATIONS = "CREATE TABLE IF NOT EXISTS " +
            NotificationsDBAdapter.DATABASE_TABLE +
            " (_id integer PRIMARY KEY AUTOINCREMENT, " +
            NotificationsDBAdapter.KEY_DATE + " timestamp NOT NULL, " +
            NotificationsDBAdapter.KEY_CATEGORY + " tinyint NOT NULL, " +
            NotificationsDBAdapter.KEY_DESCRIPTION + " varchar(150) NOT NULL, " +
            NotificationsDBAdapter.KEY_LINK + " varchar(1500) DEFAULT NULL," +
            NotificationsDBAdapter.KEY_USERID + " integer NOT NULL, " +
            ");";

    private static final String DATABASE_CREATE = CREATE_USERS + CREATE_POSTS + CREATE_NOTIFICATIONS;

    public ArtformDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + UsersDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + PostsDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + NotificationsDBAdapter.DATABASE_TABLE + ";");
        onCreate(database);
    }
}
