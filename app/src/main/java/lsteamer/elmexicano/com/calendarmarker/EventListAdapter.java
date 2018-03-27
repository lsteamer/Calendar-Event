package lsteamer.elmexicano.com.calendarmarker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import lsteamer.elmexicano.com.calendarmarker.data.Event;
import lsteamer.elmexicano.com.calendarmarker.data.EventListContract;
import lsteamer.elmexicano.com.calendarmarker.utils.ColorUtil;

/**
 * Created by lsteamer on 25/02/2018.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    // Adapter's Context
    private Context mContext;

    // Adapter's Contents
    private List<Event> mEventList;




    // Declaring an interface
    private OnItemClicked onClick;

    // And coding the interface
    public interface OnItemClicked{
        void onItemClicked(int position);
    }


    // public constructor
    public EventListAdapter(Context context, List<Event> eventList){
        this.mContext = context;
        this.mEventList = eventList;
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

        final Event event = mEventList.get(position);

       //Getting the values from the database
       String title = event.getEventTitle();
       int color = event.getEventColor();

       //getting the color Hexadecimal code
       color = ColorUtil.getColorHexInt(color);


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


    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    public void removeEvent(int position){
        mEventList.remove(position);

        notifyItemRemoved(position);

    }

    public void addEvent(Event event, int position){
        mEventList.add(position, event);

        notifyItemInserted(position);
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
