package it.artform.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Date;

public class CommissionDBAdapter {
    private Context context;
    private SQLiteDatabase database;
    private ArtformDBHelper dbHelper;

    protected static final String DATABASE_TABLE = "commission";
    protected static final String KEY_COMMISSIONID = "_id"; //PK autoincrement
    protected static final String KEY_TITLE = "title";
    protected static final String KEY_PRICE = "price";
    protected static final String KEY_DATE = "date";
    protected static final String KEY_ARTIST = "artistId";
    protected static final String KEY_CUSTOMER = "customerId";
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

    private ContentValues createContentValues(String title, double price, Date date, int artistId, int customerId, String accountAddress) {
        ContentValues values = new ContentValues();
        values.put( KEY_TITLE, title );
        values.put( KEY_PRICE, price );
        values.put( KEY_DATE, String.valueOf(date) );
        values.put( KEY_ARTIST, artistId );
        values.put( KEY_CUSTOMER, customerId );
        values.put( KEY_ACCOUNT_ADDRESS, accountAddress );
        return values;
    }

    public long storeCommission(String title, double price, Date date, int artistId, int customerId, String accountAddress) {
        ContentValues commissionValues = createContentValues(title, price, date, artistId, customerId, accountAddress);
        return database.insertOrThrow(DATABASE_TABLE, null, commissionValues);
    }

    public Cursor fetchCommissionsByArtist(int artistId) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_COMMISSIONID, KEY_TITLE, KEY_PRICE, KEY_DATE, KEY_ARTIST, KEY_CUSTOMER, KEY_ACCOUNT_ADDRESS },
                KEY_ARTIST + " = "+ artistId, null, null, null, null, null);
        return mCursor;
    }

    public Cursor fetchCommissionsByCustomer(int customerId) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_COMMISSIONID, KEY_TITLE, KEY_PRICE, KEY_DATE, KEY_ARTIST, KEY_CUSTOMER, KEY_ACCOUNT_ADDRESS },
                KEY_CUSTOMER + " = "+ customerId, null, null, null, null, null);
        return mCursor;
    }

}
