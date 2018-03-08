package lsteamer.elmexicano.com.calendarmarker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;


import lsteamer.elmexicano.com.calendarmarker.spinner.ColorAdapter;
import lsteamer.elmexicano.com.calendarmarker.spinner.ColorItem;

/**
 * Created by lsteamer on 28/02/2018.
 */

public class EventDetailActivity extends AppCompatActivity {


    // The Adapter to display the Colors
    private ColorAdapter mColorAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        // Declaring the spinner
        Spinner colorSpinner = findViewById(R.id.color_spinner);

        // New color adapter (using the final static ColorItem ArrayList declared in MainActivity)
        mColorAdapter = new ColorAdapter( this, MainActivity.mColorList);

        //Set the adapter and a listener
        colorSpinner.setAdapter(mColorAdapter);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ColorItem clickedColorItem = (ColorItem) adapterView.getItemAtPosition(i);
                String clickedColorTitle = clickedColorItem.getColorTitle();
                Toast.makeText(EventDetailActivity.this, clickedColorTitle + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
