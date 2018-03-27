package lsteamer.elmexicano.com.calendarmarker.data;

/**
 * Created by lsteamer on 23/03/2018.
 */

public class Event {

    int color;
    String title;
    String description;
    long id;

    public Event(String title, String description, int color, long id){
        this.title = title;
        this.description = description;
        this.color = color;
        this.id = id;

    }

    public int getEventColor() {
        return color;
    }

    public String getEventTitle() {
        return title;
    }

    public String getEventDescription() {
        return description;
    }

    public long getEventId(){
        return id;
    }

    public void setEventColor(int color) {
        this.color = color;
    }

    public void setEventTitle(String title) {
        this.title = title;
    }

    public void setEventDescription(String description) {
        this.description = description;
    }

    public void setEventId(Long id){
        this.id = id;
    }

}
