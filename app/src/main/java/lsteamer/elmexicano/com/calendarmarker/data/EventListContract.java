package lsteamer.elmexicano.com.calendarmarker.data;

import android.provider.BaseColumns;

/**
 * Created by lsteamer on 25/02/2018.
 */

public class EventListContract {


    public static final class EventListEntry implements BaseColumns{
        public static final String TABLE_NAME = "eventlist";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "title";
        public static final String COLUMN_COLOR = "color";
        public static final String COLUMN_REGISTRY_KIND = "fullday";

    }
}
