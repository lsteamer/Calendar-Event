package lsteamer.elmexicano.com.calendarmarker.spinner;

/**
 * Single Item
 * holder Class
 */


public class ColorItem {

    private String mColorTitle;
    private int mColorCode;

    public ColorItem(String colorTitle, int colorCode){
        mColorCode = colorCode;
        mColorTitle = colorTitle;
    }

    public String getColorTitle(){
        return mColorTitle;
    }

    public int getColorCode(){
        return mColorCode;
    }

}
