package lsteamer.elmexicano.com.calendarmarker.spinner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lsteamer.elmexicano.com.calendarmarker.R;

/**
 * Class that will give us a custom spinner. With two TextViews.
 * One showing a color and one showing the color's Title.
 */

public class ColorAdapter extends ArrayAdapter<ColorItem> {

    //Constructor for the Color Adapter
    public ColorAdapter(Context context, ArrayList<ColorItem> colorList){
        super(context, 0, colorList);
    }


    //getView and getDropDownView methods.
    //Both call the initView method down below

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }



    //Initialize the view (Both the same for the Dropdown as for the Spinner)
    private View initView(int position, View convertView, ViewGroup parent){

        //If the view is not created, inflate it.
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.color_spinner_item,
                    parent,
                    false
            );
        }

        //We get the references to the TextView in the layout
        TextView colorText = convertView.findViewById(R.id.color_text_view);
        TextView titleText = convertView.findViewById(R.id.title_text_view);

        //Reference to the current item
        ColorItem currentItem = getItem(position);

        //if the current Item is not null
        if(currentItem != null){

            //Set the color and the Title.
            colorText.setBackgroundColor(currentItem.getColorCode());
            titleText.setText(currentItem.getColorTitle());
        }

        //We return the View
        return convertView;
    }



}
