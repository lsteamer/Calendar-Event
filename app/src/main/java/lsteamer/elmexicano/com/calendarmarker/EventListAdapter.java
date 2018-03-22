package lsteamer.elmexicano.com.calendarmarker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lsteamer.elmexicano.com.calendarmarker.data.EventListContract;
import lsteamer.elmexicano.com.calendarmarker.utils.ColorUtil;

/**
 * Created by lsteamer on 25/02/2018.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    // Adapter's Context
    private Context mContext;
    // Adapter's Cursor
    private Cursor mCursor;





    // Declaring an interface
    private OnItemClicked onClick;

    // And coding the interface
    public interface OnItemClicked{
        void onItemClicked(int position);
    }


    // public constructor
    public EventListAdapter(Context context, Cursor count){
        this.mContext = context;
        this.mCursor = count;
    }



    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.event_list_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {

        // if the position doesn't exist
       if(!mCursor.moveToPosition(position))
           return;

       //Getting the values from the database
       String title = mCursor.getString(mCursor.getColumnIndex(EventListContract.EventListEntry.COLUMN_TITLE));
       int color = mCursor.getInt(mCursor.getColumnIndex(EventListContract.EventListEntry.COLUMN_COLOR));

       //getting the color Hexadecimal code
       color = ColorUtil.getColorHexInt(color);


       // Getting an ID for the swiping in MainActivity
       long id = mCursor.getLong(mCursor.getColumnIndex(EventListContract.EventListEntry._ID));

       // Set the title and the color
       holder.titleTextView.setText(title);
       holder.colorTextView.setBackgroundColor(color);


       //Adding an OnClickListener
       holder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClicked(position);
            }
       });

       // Setting the tag object with the id
       holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public void removeItem(int position){

    }

    // A method that updates the current cursor
    public void swapCursor(Cursor newCursor){

        // If the Cursor is null close it to avoid leaking resources
        if(mCursor.equals(null))
            mCursor.close();

        // Replace the old cursor with the new one
        mCursor = newCursor;

        // If the new cursor is null, and if so notify.
        if(!newCursor.equals(null))
            this.notifyDataSetChanged();

    }

    public class EventViewHolder extends RecyclerView.ViewHolder{


        public RelativeLayout viewBackground, viewForeground;

        public TextView titleTextView, colorTextView;

        // Constructior for the ViewHolder
        public EventViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            colorTextView = (TextView) itemView.findViewById(R.id.color_text_view);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
            viewForeground = (RelativeLayout) itemView.findViewById(R.id.view_foreground);

        }
    }

    // Method assigning the OnClick to the interface
    public void setOnClick(OnItemClicked onClick) {

        this.onClick=onClick;
    }


}
