/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility Class to Parse Time Object from UTC to Local and back to UTC
 *
 * @author Austin Tracy
 */
public class DateTimeParser {

    /**
     * This Method takes in a String of DateTime and returns a UTC Time Value to
     * store in DB
     *
     * @param dateTime Local Time to transform into UTC
     * @return UTC Time
     */
    public static String toUTC(String dateTime) {
        if (dateTime != null) {
            Timestamp timestamp = Timestamp.valueOf(dateTime);
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime utcZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));

            return utcZonedDateTime.toString();
        } else {
            return dateTime;
        }
    }

    /**
     * This Method takes in a String of DateTime and returns a Local Time Value
     * to display in UI
     *
     * @param dateTime UTC Time to Transform into Local
     * @return Local Time
     */
    public static String toLocal(String dateTime) {
        if (dateTime != null) {
            Timestamp timestamp = Timestamp.valueOf(dateTime);
            LocalDateTime ldt = timestamp.toLocalDateTime();
            ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
            ZonedDateTime utcZonedDateTime = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));

            return utcZonedDateTime.toString();
        } else {
            return dateTime;
        }
    }

}
