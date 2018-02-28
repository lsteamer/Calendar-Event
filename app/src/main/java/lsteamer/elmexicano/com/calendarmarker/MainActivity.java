package lsteamer.elmexicano.com.calendarmarker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import lsteamer.elmexicano.com.calendarmarker.data.EventListContract;
import lsteamer.elmexicano.com.calendarmarker.data.EventListDbHelper;

public class MainActivity extends AppCompatActivity {

    private EventListAdapter mAdapter;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Action Bar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
                startActivity(intent);
            }
        });


        RecyclerView eventlistRecyclerView;

        // Set Local attributes to corresponding views
        eventlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_events_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        eventlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Creating a DB Helper (Will create and run the DB for the first time)
        EventListDbHelper dbHelper = new EventListDbHelper(this);

        mDb = dbHelper.getWritableDatabase();

        insertFakeData(mDb);


        // COMPLETED (7) Run the getAllGuests function and store the result in a Cursor variable
        Cursor cursor = getAllGuests();


        // Create an adapter for that cursor to display data
        mAdapter = new EventListAdapter(this, cursor);

        eventlistRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private Cursor getAllGuests() {
        // Call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDb.query(
                EventListContract.EventListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                EventListContract.EventListEntry.COLUMN_TIMESTAMP
        );
    }


    ////////////////////////////////


    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "John");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noe");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 1);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Tim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Jessica");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 3);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Larry");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeeeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 4);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Kim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope nowe");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 5);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Tim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Jessica");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 3);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Larry");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeeeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 4);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Kim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope nowe");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 5);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Tim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Jessica");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 3);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Larry");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeeeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 4);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Kim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope nowe");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 5);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Tim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Jessica");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 3);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Larry");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeeeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 4);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Kim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope nowe");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 5);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Tim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Jessica");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 3);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Larry");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope noeeeee");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 4);
        list.add(cv);

        cv = new ContentValues();
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, "Kim");
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, "nope nowe");
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, 5);
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (EventListContract.EventListEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(EventListContract.EventListEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }


    ////////////////////////////////


}
