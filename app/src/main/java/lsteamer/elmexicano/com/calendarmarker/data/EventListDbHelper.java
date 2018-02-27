package lsteamer.elmexicano.com.calendarmarker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lsteamer on 25/02/2018.
 */

public class EventListDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "eventlist.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 3;

    //Constructor
    public EventListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_EVENTLIST_TABLE = "CREATE TABLE " + EventListContract.EventListEntry.TABLE_NAME + " (" +
                EventListContract.EventListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EventListContract.EventListEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                EventListContract.EventListEntry.COLUMN_DESCRIPTION + " TEXT," +
                EventListContract.EventListEntry.COLUMN_COLOR + " INT NOT NULL," +
                EventListContract.EventListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";
        // Execute the query
        sqLiteDatabase.execSQL(SQL_CREATE_EVENTLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EventListContract.EventListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
