package lsteamer.elmexicano.com.calendarmarker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import lsteamer.elmexicano.com.calendarmarker.data.EventListContract;
import lsteamer.elmexicano.com.calendarmarker.data.EventListDbHelper;
import lsteamer.elmexicano.com.calendarmarker.spinner.ColorAdapter;

/**
 * Created by lsteamer on 28/02/2018.
 */

public class EventDetailActivity extends AppCompatActivity {


    // The Adapter to display the Colors
    private ColorAdapter mColorAdapter;

    // EditTexts
    private EditText mNewEventTitle, mNewEventDescription;
    private Spinner mNewColorSpinner;

    // Color that will be changed by the spinner
    int mNewColor = 12;


    //Our Database
    private SQLiteDatabase mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        // Getting the Edit Texts references
        mNewEventTitle = (EditText) findViewById(R.id.title_edit_text);
        mNewEventDescription = (EditText) findViewById(R.id.description_edit_text);


        // Creating DB Helper
        EventListDbHelper dbHelper = new EventListDbHelper(this);

        // And getting the writable reference
        mDb = dbHelper.getWritableDatabase();



        // CODE FOR THE SPINNER BELOW

        // Finding the reference
        mNewColorSpinner = (Spinner) findViewById(R.id.color_spinner);

        // New color adapter (using the final static ColorItem ArrayList declared in MainActivity)
        mColorAdapter = new ColorAdapter( this, MainActivity.mColorList);

        //Set the adapter and a listener
        mNewColorSpinner.setAdapter(mColorAdapter);
        mNewColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // Get the color value from the Spinner
                mNewColor = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        // FloatingActionButton. Calls createNewEntry
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDetail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createNewEntry();

            }
        });

    }

    // Displaying a custom menu that will hold an 'Add' button.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    // Handling action bar item clicks
    // We only have "Add", and it calls createNewEntry
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.event_detail_add_event){
            createNewEntry();

        }


        return super.onOptionsItemSelected(item);
    }

    public void createNewEntry(){

        //If there's no title, send an error message
        if(mNewEventTitle.getText().length() == 0 ){
            Toast.makeText(this, "Title cannot be empty",
                    Toast.LENGTH_SHORT).show();

        }
        else{

            // Get the values for the Title, Description
            String title = mNewEventTitle.getText().toString();
            String description;
            if(mNewEventDescription.getText().length() != 0){
                description = mNewEventDescription.getText().toString();

            }
            else{

                // Description can be null
                description = "";
            }

            // Call the method to add the event to the Database.
            addToEventList(title, description, mNewColor);

            // Result was okay
            setResult(RESULT_OK);

            // Finish this Activity
            finish();

        }


    }


    // Method that will include a new item in the Database
    public long addToEventList(String title, String description, int color){

        // Creating a ContentValues instance to pass the values onto the insert query
        ContentValues cv = new ContentValues();

        // Call put to insert the name, description and color values
        cv.put(EventListContract.EventListEntry.COLUMN_TITLE, title);
        cv.put(EventListContract.EventListEntry.COLUMN_DESCRIPTION, description);
        cv.put(EventListContract.EventListEntry.COLUMN_COLOR, color);

        // Insert the new data.
        return mDb.insert(EventListContract.EventListEntry.TABLE_NAME, null, cv);

    }


}
