package Util;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by sidd on 07/06/17.
 */

public class DateHelper {
    public static String getReadableDate(long date){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        return dateFormat.format(date);
    }
}
