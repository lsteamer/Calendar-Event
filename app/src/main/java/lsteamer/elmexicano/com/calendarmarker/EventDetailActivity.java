package lsteamer.elmexicano.com.calendarmarker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import lsteamer.elmexicano.com.calendarmarker.spinner.ColorAdapter;
import lsteamer.elmexicano.com.calendarmarker.spinner.ColorItem;
import lsteamer.elmexicano.com.calendarmarker.utils.ColorUtil;

/**
 * Created by lsteamer on 28/02/2018.
 */

public class EventDetailActivity extends AppCompatActivity {

    private ArrayList<ColorItem> mColorList;
    private ColorAdapter mColorAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        mColorList = ColorUtil.initList();

        Spinner colorSpinner = findViewById(R.id.color_spinner);

        mColorAdapter = new ColorAdapter( this, mColorList);
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
