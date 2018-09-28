package com.samjameskennedy.contentextractor.extractor;

import java.util.Calendar;
import java.util.Date;

import org.jsoup.nodes.Element;

public class DateExtractor {

    public static Date extractDate(Element comment) {
        String timeAgo = comment.select("div>a[id^=CommentTopMeta]").select("span").text();
        return timeAgoToActualDate(timeAgo);
    }

    private static Date timeAgoToActualDate(String timeAgo) {
        if ("just now".equalsIgnoreCase(timeAgo)) return new Date();
        String[] components = timeAgo.split(" ");
        int value = Integer.parseInt(components[0]);
        String unit = components[1];
        int calendarUnit = parseUnit(unit);
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarUnit, value * -1);
        return new Date(calendar.toInstant().toEpochMilli());
    }

    private static int parseUnit(String unit) {
        switch (unit) {
            case "second":
            case "seconds":
                return Calendar.SECOND;
            case "minute":
            case "minutes":
                return Calendar.MINUTE;
            case "hour":
            case "hours":
                return Calendar.HOUR;
            case "day":
            case "days":
                return Calendar.DAY_OF_YEAR;
            case "month":
            case "months":
                return Calendar.MONTH;
            case "year":
            case "years":
                return Calendar.YEAR;
            default:
                return 0;
        }
    }
}
