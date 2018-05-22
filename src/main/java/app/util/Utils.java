package app.util;

import com.sun.scenario.effect.Offset;
import spark.QueryParamsMap;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static spark.Spark.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

public class Utils {

    /**
     * Returns a random v4 UUID as string.
     * @return String
     */
    public static String getUUIDString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Formats regular datetime object to unix timestamps (because we format after unix timestamp in DB).
     * @param dateString
     * @return long
     */
    public static int getUnixTimestampFromDateString(String dateString) {
        OffsetDateTime date = OffsetDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        int unixTimestamp = (int)date.toEpochSecond();
        System.out.println(unixTimestamp);
        return unixTimestamp;
    }
}
