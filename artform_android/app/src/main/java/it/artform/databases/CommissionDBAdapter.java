package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Date;

import it.artform.pojos.Commission;

public class CommissionDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "commission";
    protected static final String KEY_COMMISSIONID = "_id"; //PK autoincrement
    protected static final String KEY_EXTERNAL_ID = "id"; // server DB PK
    protected static final String KEY_TITLE = "title";
    protected static final String KEY_PRICE = "price";
    protected static final String KEY_DATE = "date";
    protected static final String KEY_ARTIST = "artistUsername";
    protected static final String KEY_CUSTOMER = "customerUsername";
    protected static final String KEY_ACCOUNT_ADDRESS = "accountAddress";

    public CommissionDBAdapter(Context context) {
        this.context = context;
    }

    public CommissionDBAdapter open() throws SQLException {
        dbHelper = new ArtformDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(int id, String title, double price, Date date, String artistUsername, String customerUsername, String accountAddress) {
        ContentValues values = new ContentValues();
        values.put( KEY_EXTERNAL_ID, id );
        values.put( KEY_TITLE, title );
        values.put( KEY_PRICE, price );
        values.put( KEY_DATE, String.valueOf(date) );
        values.put( KEY_ARTIST, artistUsername );
        values.put( KEY_CUSTOMER, customerUsername );
        values.put( KEY_ACCOUNT_ADDRESS, accountAddress );
        return values;
    }

    public long storeCommission(Commission c) {
        ContentValues commissionValues = createContentValues(c.getId(), c.getTitle(), c.getPrice(), c.getDate(), c.getArtist(), c.getCustomer(), c.getAccountAddress());
        return database.insertOrThrow(DATABASE_TABLE, null, commissionValues);
    }

    public Cursor fetchCommissionsByArtist(String artistUsername) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_COMMISSIONID, KEY_TITLE, KEY_PRICE, KEY_DATE, KEY_ARTIST, KEY_CUSTOMER, KEY_ACCOUNT_ADDRESS },
                KEY_ARTIST + " = "+ artistUsername, null, null, null, null, null);
        return mCursor;
    }

    public Cursor fetchCommissionsByCustomer(String customerUsername) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_COMMISSIONID, KEY_TITLE, KEY_PRICE, KEY_DATE, KEY_ARTIST, KEY_CUSTOMER, KEY_ACCOUNT_ADDRESS },
                KEY_CUSTOMER + " = "+ customerUsername, null, null, null, null, null);
        return mCursor;
    }

}
