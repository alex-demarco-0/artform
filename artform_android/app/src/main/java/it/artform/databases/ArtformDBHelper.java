package it.artform.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArtformDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "artform.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS " +
            UserDBAdapter.DATABASE_TABLE +
            " (" + UserDBAdapter.KEY_USERID + " integer PRIMARY KEY AUTOINCREMENT, " +
            UserDBAdapter.KEY_NAME + " varchar(50) NOT NULL, " +
            UserDBAdapter.KEY_SURNAME + " varchar(50) NOT NULL, " +
            UserDBAdapter.KEY_USERNAME + " varchar(50) NOT NULL, " +
            UserDBAdapter.KEY_EMAIL + " varchar(50) NOT NULL," +
            UserDBAdapter.KEY_PHONE + " varchar(10) DEFAULT NULL, " +
            UserDBAdapter.KEY_PASSWORD + " varchar(50) NOT NULL, " +
            UserDBAdapter.KEY_POINTS + " integer NOT NULL DEFAULT '0');";
    private static final String CREATE_POST = "CREATE TABLE IF NOT EXISTS " +
            PostDBAdapter.DATABASE_TABLE +
            " (" + PostDBAdapter.KEY_POSTID + " integer PRIMARY KEY AUTOINCREMENT, " +
            PostDBAdapter.KEY_USER + " integer NOT NULL, " +
            PostDBAdapter.KEY_TITLE + " varchar(50) NOT NULL, " +
            PostDBAdapter.KEY_TOPIC + " varchar(50) NOT NULL, " +
            PostDBAdapter.KEY_TAGS + " varchar(100) NOT NULL, " +
            PostDBAdapter.KEY_PUBLICATION_DATE + " timestamp NOT NULL, " +
            PostDBAdapter.KEY_LIKE + " integer NOT NULL DEFAULT '0', " +
            PostDBAdapter.KEY_TYPE + " boolean NOT NULL);";
    private static final String CREATE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " +
            NotificationDBAdapter.DATABASE_TABLE +
            " (" + NotificationDBAdapter.KEY_NOTIFICATIONID + " integer PRIMARY KEY AUTOINCREMENT, " +
            NotificationDBAdapter.KEY_DATE + " timestamp NOT NULL, " +
            NotificationDBAdapter.KEY_CATEGORY + " tinyint NOT NULL, " +
            NotificationDBAdapter.KEY_DESCRIPTION + " varchar(150) NOT NULL, " +
            NotificationDBAdapter.KEY_LINK + " varchar(1500) DEFAULT NULL," +
            NotificationDBAdapter.KEY_USER + " integer NOT NULL);";
    private static final String CREATE_COMMISSION = "CREATE TABLE IF NOT EXISTS " +
            CommissionDBAdapter.DATABASE_TABLE +
            " (" + CommissionDBAdapter.KEY_COMMISSIONID + " integer PRIMARY KEY AUTOINCREMENT, " +
            CommissionDBAdapter.KEY_TITLE + " varchar(100) NOT NULL, " +
            CommissionDBAdapter.KEY_PRICE + " double NOT NULL, " +
            CommissionDBAdapter.KEY_DATE + " timestamp NOT NULL, " +
            CommissionDBAdapter.KEY_ARTIST + " integer NOT NULL," +
            CommissionDBAdapter.KEY_CUSTOMER + " integer NOT NULL, " +
            CommissionDBAdapter.KEY_ACCOUNT_ADDRESS + " varchar(50) NOT NULL);";
    private static final String CREATE_TOPIC = "CREATE TABLE IF NOT EXISTS " +
            TopicDBAdapter.DATABASE_TABLE +
            " (" + TopicDBAdapter.KEY_TOPICID + " integer PRIMARY KEY AUTOINCREMENT, " +
            TopicDBAdapter.KEY_NAME + " varchar(30) NOT NULL);";

    //private static final String DATABASE_CREATE = CREATE_USER + CREATE_POST + CREATE_NOTIFICATION;

    public ArtformDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //database.execSQL(DATABASE_CREATE);
        database.execSQL(CREATE_USER);
        database.execSQL(CREATE_POST);
        database.execSQL(CREATE_NOTIFICATION);
        database.execSQL(CREATE_COMMISSION);
        database.execSQL(CREATE_TOPIC);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + UserDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + PostDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + NotificationDBAdapter.DATABASE_TABLE + ";");
        onCreate(database);
    }
}
