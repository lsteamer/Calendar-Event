package lsteamer.elmexicano.com.calendarmarker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lsteamer.elmexicano.com.calendarmarker.data.EventListContract;
import lsteamer.elmexicano.com.calendarmarker.data.EventListDbHelper;
import lsteamer.elmexicano.com.calendarmarker.spinner.ColorItem;
import lsteamer.elmexicano.com.calendarmarker.utils.ColorUtil;
import lsteamer.elmexicano.com.calendarmarker.utils.RecyclerItemTouchHelper;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener  {



    // An Array of ColorItem that will show the colors in the spinner.
    // Information is included by a static method
    public final static  ArrayList<ColorItem> mColorList = ColorUtil.initList();

    //The Adapter for the list that will display the events
    private EventListAdapter mAdapter;

    //Our Database
    private SQLiteDatabase mDb;

    private List<String> somesome;



    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);



        if(somesome==null){
            Toast.makeText(this, "Shit is null yo'",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Shit is no longer null",
                    Toast.LENGTH_SHORT).show();
        }



        // ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Action Bar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On Click, launch a new activity (EventDetailActivity
                Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
                startActivity(intent);

            }
        });


        // Create a RecyclerView and set Local attributes to corresponding views
        RecyclerView eventlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_events_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        eventlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Creating a DB Helper (Will create and run the DB for the first time)
        EventListDbHelper dbHelper = new EventListDbHelper(this);

        // We Get a writableDatabase reference
        mDb = dbHelper.getWritableDatabase();


        // Run the getAllEvents function and store the result in a Cursor variable
        Cursor cursor = getAllEvents();


        // Create an adapter for that cursor to display data
        mAdapter = new EventListAdapter(this, cursor);


        // Set the Adapter
        eventlistRecyclerView.setAdapter(mAdapter);


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(eventlistRecyclerView);



    }


    @Override
    protected void onRestart() {
        super.onRestart();

        // When coming back to the activity, update the Cursor & then the adapter
        mAdapter.swapCursor(getAllEvents());

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position){

        // Get the id from the tag
        final long idDeletion = (long) viewHolder.itemView.getTag();




        // showing snack bar with Undo option
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "some" + " removed from cart!", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Update the adapter
                mAdapter.swapCursor(getAllEvents());
            }
        }).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int dismissType) {
                super.onDismissed(snackbar, dismissType);

                if(dismissType != DISMISS_EVENT_ACTION){


                    // Delete the event
                    removeEvent(idDeletion);

                    // Update the adapter
                    mAdapter.swapCursor(getAllEvents());
                }


            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Returning the result in a type Cursor
    private Cursor getAllEvents() {
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

    // Method that will remove an event from the list
    private boolean removeEvent(long id){

        // calling the delete method on the database reference.
        // And checking if something was actually deleted and returning that as a boolean
        return mDb.delete(EventListContract.EventListEntry.TABLE_NAME,
                EventListContract.EventListEntry._ID + "=" + id, null) > 0;
    }


}
