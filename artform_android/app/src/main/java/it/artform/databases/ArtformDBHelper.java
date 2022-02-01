package it.artform.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArtformDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "artform.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " + UsersDBAdapter.DATABASE_TABLE +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + ");";
    //create varie tabelle

    public ArtformDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS site");
        onCreate(database);
    }
}
