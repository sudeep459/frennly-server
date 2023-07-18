package com.frennly.ds.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LastSeenMessageGenerator {
    public static String getLastSeenMessage(Timestamp timestamp) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        long timeDifference = currentTimestamp.getTime() - timestamp.getTime();

        // Calculate time in seconds, minutes, hours, and days
        long seconds = timeDifference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 7) {
            // If the last seen was more than a week ago, return a specific date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date lastSeenDate = new Date(timestamp.getTime());
            return "Last seen on " + dateFormat.format(lastSeenDate);
        } else if (days > 0) {
            // If the last seen was within a week, return the day of the week
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            Date lastSeenDate = new Date(timestamp.getTime());
            return "Last seen " + dateFormat.format(lastSeenDate);
        } else if (hours > 0) {
            // If the last seen was within the same day, return the hours and minutes
            return "Last seen today at " + formatTime(timestamp);
        } else if (minutes > 0) {
            // If the last seen was within the same hour, return the minutes
            return "Last seen " + minutes + " minutes ago";
        } else {
            // If the last seen was within the same minute, return "Online"
            return "Online";
        }
    }

    private static String formatTime(Timestamp timestamp) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        Date lastSeenDate = new Date(timestamp.getTime());
        return timeFormat.format(lastSeenDate);
    }
}
