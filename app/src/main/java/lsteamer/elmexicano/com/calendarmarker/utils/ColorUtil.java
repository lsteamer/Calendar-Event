package lsteamer.elmexicano.com.calendarmarker.utils;

/**
 * Created by lsteamer on 28/02/2018.
 */

public class ColorUtil {

    public static String getColorName(int color){

        switch (color){
            case 1:
                return "Tomato";
            case 2:
                return "Tangerine";
            case 3:
                return "Banana";
            case 4:
                return "Basil";
            case 5:
                return "Sage";
            case 6:
                return "Peacock";
            case 7:
                return "Blueberry";
            case 8:
                return "Lavender";
            case 9:
                return "Grape";
            case 10:
                return "Flamingo";
            case 11:
                return "Graphite";
            case 12:
                return "Default Blue";
            default:
                return "Default Blue";
        }

    }

    public static int getColorHexInt(int color){

        switch (color){
            case 1:
                return 0xff000000 + Integer.parseInt("D50000",16);
            case 2:
                return 0xff000000 + Integer.parseInt("F4511E",16);
            case 3:
                return 0xff000000 + Integer.parseInt("F6BF26",16);
            case 4:
                return 0xff000000 + Integer.parseInt("0B8043",16);
            case 5:
                return 0xff000000 + Integer.parseInt("FBB679",16);
            case 6:
                return 0xff000000 + Integer.parseInt("039B81",16);
            case 7:
                return 0xff000000 + Integer.parseInt("3F51B5",16);
            case 8:
                return 0xff000000 + Integer.parseInt("7986CB",16);
            case 9:
                return 0xff000000 + Integer.parseInt("8E24AA",16);
            case 10:
                return 0xff000000 + Integer.parseInt("E67C73",16);
            case 11:
                return 0xff000000 + Integer.parseInt("616161",16);
            case 12:
                return 0xff000000 + Integer.parseInt("4285F4",16);
            default:
                return 0xff000000 + Integer.parseInt("4285F4",16);
        }
    }

}
