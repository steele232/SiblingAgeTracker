package steele.apps.siblingagetracker.db;

import android.arch.persistence.room.TypeConverter;

import java.util.GregorianCalendar;

/**
 * Created by jonathansteele on 3/10/18.
 */

public class GregCalendarConverter {

    @TypeConverter
    public static GregorianCalendar fromTimestamp(Long timeInMillis) {
        if (timeInMillis != null) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(timeInMillis);
            return calendar;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static Long fromCalendar(GregorianCalendar calendar) {
        if (calendar != null) {
            return calendar.getTimeInMillis();
        } else {
            return null;
        }
    }

}
