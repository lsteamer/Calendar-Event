package lsteamer.elmexicano.com.calendarmarker;

import android.content.Intent;
import android.database.Cursor;
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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lsteamer.elmexicano.com.calendarmarker.data.Event;
import lsteamer.elmexicano.com.calendarmarker.data.EventListContract;
import lsteamer.elmexicano.com.calendarmarker.data.EventListDbHelper;
import lsteamer.elmexicano.com.calendarmarker.spinner.ColorItem;
import lsteamer.elmexicano.com.calendarmarker.utils.ColorUtil;
import lsteamer.elmexicano.com.calendarmarker.utils.RecyclerItemTouchHelper;

public class MainActivity extends AppCompatActivity implements EventListAdapter.OnItemClicked, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener  {


    // An Array of ColorItem that will show the colors in the spinner.
    // Information is included by a static method
    public final static  ArrayList<ColorItem> mColorList = ColorUtil.initList();


    //The Adapter for the list that will display the events
    private EventListAdapter mAdapter;


    private String[] eventTableFields = {EventListContract.EventListEntry.COLUMN_TITLE, EventListContract.EventListEntry.COLUMN_DESCRIPTION, EventListContract.EventListEntry.COLUMN_COLOR, EventListContract.EventListEntry._ID};

    // List that will hold the information to be passed to the Adapter
    private List<Event> eventList;

    //Our Database
    private SQLiteDatabase mDb;

    // code for the request
    static final int EVENT_DETAIL_REQUEST = 261;

    // code for the Intent
    static final int EVENT_DETAIL_RESULT = 300;

    // Coordinator Layout that will be used with the Snackbar
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);


        // ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Action Bar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // On Click, launch a new activity (EventDetailActivity) and we'll be expecting a boolean back
                Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
                startActivityForResult(intent, EVENT_DETAIL_REQUEST);
            }
        });

        // Create a RecyclerView and set Local attributes to corresponding views
        RecyclerView eventlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_events_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        eventlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Creating a DB Helper (Will create and run the DB for the first time)
        EventListDbHelper dbHelper = new EventListDbHelper(this);

        // We Get a writable reference of our Database
        mDb = dbHelper.getWritableDatabase();


        // We get eventList filled with the Data in the Database
        eventList = getEventList();


        // Create an adapter for that cursor to display data
        mAdapter = new EventListAdapter(this, eventList);

        // Set the Adapter
        eventlistRecyclerView.setAdapter(mAdapter);

        // Setting the listener to the Adapter
        mAdapter.setOnClick(this);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(eventlistRecyclerView);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EVENT_DETAIL_REQUEST){
            if(resultCode == RESULT_OK){
                //If we're coming back from EventDetail and the resultCode is RESULT_OKAY

                // Get all events
                Cursor cursor = getAllEvents();

                // Get the indexes
                int [] indexes = getIndexes(cursor);

                // Move the cursor at the end
                cursor.moveToLast();

                // Get the data needed for the Event and get the object
                String title = cursor.getString(indexes[0]);
                String description = cursor.getString(indexes[1]);
                int color = cursor.getInt(indexes[2]);
                long id = cursor.getLong(indexes[3]);
                Event newEvent = new Event(title, description, color, id);

                // Add the event to the list
                eventList.add(newEvent);

            }
        }
    }


    // Method handling swipes done on the RecyclerView
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position){

        // Get a copy of the Event to restore it
        final Event deletedEvent = eventList.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();



        // Get the ID to delete it from the database
        final long idDeletion = deletedEvent.getEventId();

        // Get the Title to display it on the notification
        String deletedTitle = deletedEvent.getEventTitle();

        //Remove the Event
        mAdapter.removeEvent(viewHolder.getAdapterPosition());

        // Showing snackbar with Undo option
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Event " + deletedTitle +" removed from list", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Restoring the item in the Adapter if undo is clicked.
                mAdapter.addEvent(deletedEvent, deletedIndex);

            }
        }).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int dismissType) {
                super.onDismissed(snackbar, dismissType);
                if(dismissType != DISMISS_EVENT_ACTION){


                    // Deleting the event if Undo is not clicked
                    removeEventDb(idDeletion);
                }
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();

    }


    // Method handling clicks done on the RecyclerView
    @Override
    public void onItemClicked(int position) {
        // DUMMY TOAST. The events will be registered here
        Toast.makeText(this, "Item " + position + " clicked",
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // Returning the contents of the database in an ArrayList
    private ArrayList<Event> getEventList(){

        // Declaring the ArrayList that will be sent back
        ArrayList<Event> eventList = new ArrayList<Event>();

        // Querying to get the cursor with the Database
        Cursor cursor = getAllEvents();

        // Get the indexes
        int [] indexes = getIndexes(cursor);


        // Cycling through the cursor to get all the data
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            String title = cursor.getString(indexes[0]);
            String description = cursor.getString(indexes[1]);
            int color = cursor.getInt(indexes[2]);
            long id = cursor.getLong(indexes[3]);

            eventList.add(new Event(title, description, color, id));
        }

        return eventList;
    }


    // Returning the result of the whole list in type Cursor
    private Cursor getAllEvents() {
        // Call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDb.query(
                EventListContract.EventListEntry.TABLE_NAME,
                eventTableFields,
                null,
                null,
                null,
                null,
                EventListContract.EventListEntry.COLUMN_TIMESTAMP
        );
    }


    // Method that given a cursor, provides an array with the indexes of this table
    private int[] getIndexes(Cursor cursor){

        int [] indexes = {
                cursor.getColumnIndex(EventListContract.EventListEntry.COLUMN_TITLE),
                cursor.getColumnIndex(EventListContract.EventListEntry.COLUMN_DESCRIPTION),
                cursor.getColumnIndex(EventListContract.EventListEntry.COLUMN_COLOR),
                cursor.getColumnIndex(EventListContract.EventListEntry._ID)};

        return indexes;
    }


    // Method that will remove an event from the database
    private boolean removeEventDb(long id){

        // calling the delete method on the database reference.
        // And checking if something was actually deleted and returning that as a boolean
        return mDb.delete(EventListContract.EventListEntry.TABLE_NAME,
                EventListContract.EventListEntry._ID + "=" + id, null) > 0;
    }



}
