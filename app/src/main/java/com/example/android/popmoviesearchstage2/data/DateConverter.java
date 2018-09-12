package com.example.android.popmoviesearchstage2.data;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;


public class DateConverter {

     @TypeConverter
    public static Date toYear(String releaseDate) {
        return releaseDate == null ? null : new Date(releaseDate);
    }

    @TypeConverter
    public static String toTimestamp(Calendar calendar) {
        if (calendar == null) {
            return null;
        } else {
            calendar.get ( Calendar.YEAR );

        }
        return String.valueOf ( calendar.get ( Calendar.YEAR ) );
    }
}



    /**REFERENCE
     * public static String getYear(String dateString) {
    Calendar calendar = Calendar.getInstance ();
    SimpleDateFormat parser = new SimpleDateFormat ( "yyyy-MM-dd" );
    Date date = null;
        try {
        date = parser.parse ( dateString );
    } catch (ParseException e) {
        e.printStackTrace ();
    }
        calendar.setTime ( date );
        return String.valueOf ( calendar.get ( Calendar.YEAR ) );
}**/

