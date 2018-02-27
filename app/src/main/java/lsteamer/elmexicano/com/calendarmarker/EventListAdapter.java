package lsteamer.elmexicano.com.calendarmarker;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lsteamer.elmexicano.com.calendarmarker.data.EventListContract;

/**
 * Created by lsteamer on 25/02/2018.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private Context mContext;

    private Cursor mCursor;


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
    public void onBindViewHolder(EventViewHolder holder, int position) {
       if(!mCursor.moveToPosition(position))
           return;

       String title = mCursor.getString(mCursor.getColumnIndex(EventListContract.EventListEntry.COLUMN_TITLE));
       int color = mCursor.getInt(mCursor.getColumnIndex(EventListContract.EventListEntry.COLUMN_COLOR));

       String strColor = Integer.toHexString(color);


       holder.titleTextView.setText(title);
       holder.colorTextView.setText(strColor);
       holder.colorTextView.setBackgroundColor(0xff000000 + Integer.parseInt(strColor,16));

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView colorTextView;


        public EventViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            colorTextView = (TextView) itemView.findViewById(R.id.color_text_view);

        }
    }


}