package it.artform.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.Date;

public class ArtformDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "artform.db";
    private static final int DATABASE_VERSION = 3;
    // create user table
    private static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS " +
            UserDBAdapter.DATABASE_TABLE +
            " (" + UserDBAdapter.KEY_USERID + " integer PRIMARY KEY AUTOINCREMENT, " +
            UserDBAdapter.KEY_NAME + " varchar(50) NOT NULL, " +
            UserDBAdapter.KEY_SURNAME + " varchar(50) NOT NULL, " +
            UserDBAdapter.KEY_USERNAME + " varchar(50) UNIQUE NOT NULL, " +
            UserDBAdapter.KEY_EMAIL + " varchar(50) UNIQUE NOT NULL," +
            UserDBAdapter.KEY_PHONE + " varchar(10) DEFAULT NULL, " +
            UserDBAdapter.KEY_PASSWORD + " varchar(50) NOT NULL, " +
            UserDBAdapter.KEY_POINTS + " integer NOT NULL DEFAULT '0', " +
            UserDBAdapter.KEY_PROFILEPIC + " varchar(300) NOT NULL);";
    // create post table
    private static final String CREATE_POST = "CREATE TABLE IF NOT EXISTS " +
            PostDBAdapter.DATABASE_TABLE +
            " (" + PostDBAdapter.KEY_POSTID + " integer PRIMARY KEY AUTOINCREMENT, " +
            PostDBAdapter.KEY_EXTERNAL_ID + " integer UNIQUE NOT NULL, " +
            PostDBAdapter.KEY_USER + " varchar(50) NOT NULL, " +
            PostDBAdapter.KEY_TITLE + " varchar(50) NOT NULL, " +
            PostDBAdapter.KEY_TOPIC + " varchar(50) NOT NULL, " +
            PostDBAdapter.KEY_TAGS + " varchar(100) NOT NULL, " +
            PostDBAdapter.KEY_PUBLICATION_DATE + " timestamp NOT NULL, " +
            PostDBAdapter.KEY_LIKE + "integer NOT NULL DEFAULT '0', " +
            PostDBAdapter.KEY_TYPE + " boolean NOT NULL, " +
            PostDBAdapter.KEY_CONTENT + " varchar(300) NOT NULL, " +
            "FOREIGN KEY (" + PostDBAdapter.KEY_USER + ") " +
            "REFERENCES " + UserDBAdapter.DATABASE_TABLE + " (" + UserDBAdapter.KEY_USERNAME + "), " +
            "FOREIGN KEY (" + PostDBAdapter.KEY_TOPIC + ") " +
            "REFERENCES " + TopicDBAdapter.DATABASE_TABLE + " (" + TopicDBAdapter.KEY_NAME + "));";
    // create notification table
    private static final String CREATE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " +
            NotificationDBAdapter.DATABASE_TABLE +
            " (" + NotificationDBAdapter.KEY_NOTIFICATIONID + " integer PRIMARY KEY AUTOINCREMENT, " +
            NotificationDBAdapter.KEY_DATE + " timestamp UNIQUE NOT NULL, " +
            NotificationDBAdapter.KEY_CATEGORY + " tinyint NOT NULL, " +
            NotificationDBAdapter.KEY_DESCRIPTION + " varchar(150) NOT NULL, " +
            NotificationDBAdapter.KEY_LINK + " varchar(1500) DEFAULT NULL, " +
            NotificationDBAdapter.KEY_USER + " varchar(50) NOT NULL, " +
            "FOREIGN KEY (" + NotificationDBAdapter.KEY_USER + ") " +
            "REFERENCES " + UserDBAdapter.DATABASE_TABLE + " (" + UserDBAdapter.KEY_USERNAME + "));";
    // create commission table
    private static final String CREATE_COMMISSION = "CREATE TABLE IF NOT EXISTS " +
            CommissionDBAdapter.DATABASE_TABLE +
            " (" + CommissionDBAdapter.KEY_COMMISSIONID + " integer PRIMARY KEY AUTOINCREMENT, " +
            PostDBAdapter.KEY_EXTERNAL_ID + " integer UNIQUE NOT NULL, " +
            CommissionDBAdapter.KEY_TITLE + " varchar(100) NOT NULL, " +
            CommissionDBAdapter.KEY_PRICE + " double NOT NULL, " +
            CommissionDBAdapter.KEY_DATE + " timestamp NOT NULL, " +
            CommissionDBAdapter.KEY_ARTIST + " varchar(50) NOT NULL, " +
            CommissionDBAdapter.KEY_CUSTOMER + " varchar(50) NOT NULL, " +
            CommissionDBAdapter.KEY_ACCOUNT_ADDRESS + " varchar(50) NOT NULL, " +
            "FOREIGN KEY (" + CommissionDBAdapter.KEY_ARTIST + ") " +
            "REFERENCES " + UserDBAdapter.DATABASE_TABLE + " (" + UserDBAdapter.KEY_USERNAME + "), " +
            "FOREIGN KEY (" + CommissionDBAdapter.KEY_CUSTOMER + ") " +
            "REFERENCES " + UserDBAdapter.DATABASE_TABLE + " (" + UserDBAdapter.KEY_USERNAME + "));";
    // create topic table
    private static final String CREATE_TOPIC = "CREATE TABLE IF NOT EXISTS " +
            TopicDBAdapter.DATABASE_TABLE +
            " (" + TopicDBAdapter.KEY_TOPICID + " integer PRIMARY KEY AUTOINCREMENT, " +
            TopicDBAdapter.KEY_NAME + " varchar(30) UNIQUE NOT NULL);";
    // create badge table
    private static final String CREATE_BADGE = "CREATE TABLE IF NOT EXISTS " +
            BadgeDBAdapter.DATABASE_TABLE +
            " (" + BadgeDBAdapter.KEY_BADGEID + " integer PRIMARY KEY AUTOINCREMENT, " +
            BadgeDBAdapter.KEY_NAME + " varchar(30) UNIQUE NOT NULL, " +
            BadgeDBAdapter.KEY_DESCRIPTION + " varchar(100) NOT NULL, " +
            BadgeDBAdapter.KEY_POINTS + " integer NOT NULL);";
    // create saved_posts table
    private static final String CREATE_SAVED_POSTS = "CREATE TABLE IF NOT EXISTS " +
            SavedPostsDBAdapter.DATABASE_TABLE +
            " (" + SavedPostsDBAdapter.KEY_SAVED_POST_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
            SavedPostsDBAdapter.KEY_USER_USERNAME + " varchar(50) NOT NULL, " +
            SavedPostsDBAdapter.KEY_POST_ID + " integer NOT NULL, " +
            "FOREIGN KEY (" + SavedPostsDBAdapter.KEY_USER_USERNAME + ") " +
            "REFERENCES " + UserDBAdapter.DATABASE_TABLE + " (" + UserDBAdapter.KEY_USERNAME + "), " +
            "FOREIGN KEY (" + SavedPostsDBAdapter.KEY_POST_ID + ") " +
            "REFERENCES " + PostDBAdapter.DATABASE_TABLE + " (" + PostDBAdapter.KEY_EXTERNAL_ID + "));";
    // create user_notification table
    private static final String CREATE_USER_NOTIFICATIONS = "CREATE TABLE IF NOT EXISTS " +
            UserNotificationsDBAdapter.DATABASE_TABLE +
            " (" + UserNotificationsDBAdapter.KEY_USER_NOTIFICATIONS_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
            UserNotificationsDBAdapter.KEY_USER_USERNAME + " integer NOT NULL, " +
            UserNotificationsDBAdapter.KEY_USER_EXT_USERNAME + " integer NOT NULL, " +
            "FOREIGN KEY (" + UserNotificationsDBAdapter.KEY_USER_USERNAME + ") " +
            "REFERENCES " + UserDBAdapter.DATABASE_TABLE + " (" + UserDBAdapter.KEY_USERNAME + "), " +
            "FOREIGN KEY (" + UserNotificationsDBAdapter.KEY_USER_EXT_USERNAME + ") " +
            "REFERENCES " + UserDBAdapter.DATABASE_TABLE + " (" + UserDBAdapter.KEY_USERNAME + "));";
    // create user_badge table
    private static final String CREATE_USER_BADGES = "CREATE TABLE IF NOT EXISTS " +
            UserBadgesDBAdapter.DATABASE_TABLE +
            " (" + UserBadgesDBAdapter.KEY_USER_BADGES_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
            UserBadgesDBAdapter.KEY_USER_USERNAME + " integer NOT NULL, " +
            UserBadgesDBAdapter.KEY_BADGE_NAME + " integer NOT NULL, " +
            "FOREIGN KEY (" + UserBadgesDBAdapter.KEY_USER_USERNAME + ") " +
            "REFERENCES " + UserDBAdapter.DATABASE_TABLE + " (" + UserDBAdapter.KEY_USERNAME + "), " +
            "FOREIGN KEY (" + UserBadgesDBAdapter.KEY_BADGE_NAME + ") " +
            "REFERENCES " + BadgeDBAdapter.DATABASE_TABLE + " (" + BadgeDBAdapter.KEY_NAME + "));";

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
        database.execSQL(CREATE_BADGE);
        database.execSQL(CREATE_SAVED_POSTS);
        database.execSQL(CREATE_USER_NOTIFICATIONS);
        database.execSQL(CREATE_USER_BADGES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + UserDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + PostDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + NotificationDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + CommissionDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + TopicDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + BadgeDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + SavedPostsDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + UserNotificationsDBAdapter.DATABASE_TABLE + ";");
        database.execSQL("DROP TABLE IF EXISTS " + UserBadgesDBAdapter.DATABASE_TABLE + ";");
        onCreate(database);
    }

}
