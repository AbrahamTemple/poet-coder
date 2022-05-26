package com.common.utils;

import com.benliu.common.utils.DecimalUtil;
import com.benliu.common.utils.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @author Huangyin
 * @since 25/5/2022
 */
public class DateUtils {
    public static final String YYYYMMDDHHMISS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_T_HH_MI_SS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD_HH_MI_SS = "MM-dd HH:mm:ss";
    public static final String MM_DD_HH_MI = "MM-dd HH:mm";
    public static final String US_DATE = "EEE MMM dd HH:mm:ss z yyyy";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY = "yyyy";
    public static final String START_DATE_SUFFIX = " 00:00:00";
    public static final String END_DATE_SUFFIX = " 23:59:59";
    private static final String DATE_SUFFIX_DAY = " 00:00:00";
    private static final String DATE_SUFFIX_MINUTE = ":00:00";
    private static final String DATE_SUFFIX_SECOND = ":00";
    public static final String TIME_ZONE_SHANGHAI = "GMT+8";
    private static final int ARRAY_LEN_2 = 2;

    public DateUtils() {
    }

    public static Date beforeHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(10, -hour);
        return calendar.getTime();
    }

    public static Date add(Date date, int calendarUnit, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarUnit, offset);
        return calendar.getTime();
    }

    public static Date afterHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(10, hour);
        return calendar.getTime();
    }

    public static Date beforeHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(10, -hour);
        return calendar.getTime();
    }

    public static Date afterHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(10, hour);
        return calendar.getTime();
    }

    public static Date afterMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(12, minute);
        return calendar.getTime();
    }

    public static String startDate(String dateStr) {
        return dateStr.concat(" 00:00:00");
    }

    public static String endDate(String dateStr) {
        return dateStr.concat(" 23:59:59");
    }

    public static Date getFullDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateStr.contains("T") ? "yyyy-MM-dd'T'HH:mm:ss" : "yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateStr);
        } catch (ParseException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String getCurrentDate(String format) {
        Calendar day = Calendar.getInstance();
        day.add(5, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(day.getTime());
        return date;
    }

    public static String getDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static Date getSimpleDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (ParseException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Date getDate(String dateStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Date getDate(Date date, String formate) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return strToDate(bartDateFormat.format(date));
        }
    }

    public static Date getFormateDate(Date date, String formate) {
        if (date == null) {
            return null;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat newFormat = new SimpleDateFormat(formate);
                String sourceDate = sdf.format(date);
                return newFormat.parse(sourceDate);
            } catch (ParseException var5) {
                var5.printStackTrace();
                return date;
            }
        }
    }

    public static Date strToDate(String birth) {
        Date d = null;
        if (StringUtils.isNotBlank(birth)) {
            d = java.sql.Date.valueOf(birth);
        }

        return d;
    }

    public static Date getDate(String dateStr) throws ParseException {
        if (dateStr.length() > 23) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            return sdf.parse(dateStr);
        } else {
            return dateStr.length() > 8 && dateStr.contains(":") ? getFullDate(dateStr) : getSimpleDate(dateStr);
        }
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static boolean inTimeRange(Date[] range, TimeUnit unit) {
        boolean result = false;
        if (null != range && 2 == range.length) {
            String formate = "yyyy-MM-dd";
            String surfix = " 00:00:00";
            if (TimeUnit.HOURS.equals(unit)) {
                formate = "yyyy-MM-dd HH";
                surfix = ":00:00";
            } else if (TimeUnit.MINUTES.equals(unit)) {
                formate = "yyyy-MM-dd HH:mm";
                surfix = ":00";
            } else if (TimeUnit.SECONDS.equals(unit)) {
                formate = "yyyy-MM-dd HH:mm:ss";
                surfix = "";
            }

            Date start = getDate("yyyy-MM-dd HH:mm:ss", formatDate(range[0], formate).concat(surfix));
            Date end = getDate("yyyy-MM-dd HH:mm:ss", formatDate(range[1], formate).concat(surfix));
            Date now = new Date();
            if (range[0].after(range[1])) {
                result = (now.before(start) || now.equals(start)) && (now.after(end) || now.equals(end));
            } else {
                result = (now.after(start) || now.equals(start)) && (now.before(end) || now.equals(end));
            }
        }

        return result;
    }

    public static Date parseDate(String dateStr) {
        try {
            String newValue = dateStr.replaceAll("[-:\\s]", "");
            if (StringUtils.isNotBlank(newValue)) {
                int year = 0;
                int month = 0;
                int day = 0;
                int hour = 0;
                int minute = 0;
                int second = 0;
                int length = newValue.length();
                if (length >= 4) {
                    year = Integer.parseInt(newValue.substring(0, 4));
                }

                if (length >= 6) {
                    month = Integer.parseInt(newValue.substring(4, 6));
                }

                if (length >= 8) {
                    day = Integer.parseInt(newValue.substring(6, 8));
                }

                if (length >= 10) {
                    hour = Integer.parseInt(newValue.substring(8, 10));
                }

                if (length >= 12) {
                    minute = Integer.parseInt(newValue.substring(10, 12));
                }

                if (length >= 14) {
                    second = Integer.parseInt(newValue.substring(12, 14));
                }

                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
                Date date = Date.from(dateTime.atZone(zoneId).toInstant());
                return date;
            }
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        return null;
    }

    public static Date convertQuarterDate(Date date) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, zone);
        int minutes = ldt.getMinute();
        ldt = ldt.withSecond(0);
        ldt = ldt.withNano(0);
        if (minutes > 45) {
            ldt = ldt.plusMinutes((long)(60 - minutes));
        } else if (minutes > 30) {
            ldt = ldt.plusMinutes((long)(45 - minutes));
        } else if (minutes > 15) {
            ldt = ldt.plusMinutes((long)(30 - minutes));
        } else if (minutes > 0) {
            ldt = ldt.plusMinutes((long)(15 - minutes));
        }

        instant = ldt.atZone(zone).toInstant();
        Date firstDate = Date.from(instant);
        return firstDate;
    }

    public static Date convertBeforeQuarterDate(Date date) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, zone);
        int minutes = ldt.getMinute();
        ldt = ldt.withSecond(0);
        ldt = ldt.withNano(0);
        if (minutes > 45) {
            ldt = ldt.minusMinutes((long)(minutes - 45));
        } else if (minutes > 30) {
            ldt = ldt.minusMinutes((long)(minutes - 30));
        } else if (minutes > 15) {
            ldt = ldt.minusMinutes((long)(minutes - 15));
        } else if (minutes > 0) {
            ldt = ldt.minusMinutes((long)minutes);
        }

        instant = ldt.atZone(zone).toInstant();
        Date firstDate = Date.from(instant);
        return firstDate;
    }

    public static Date convertNearestQuarterDate(Date date) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, zone);
        int minutes = ldt.getMinute();
        ldt = ldt.withSecond(0);
        ldt = ldt.withNano(0);
        if (minutes < 7) {
            ldt = ldt.minusMinutes((long)minutes);
        } else if (minutes >= 7 && minutes < 22) {
            ldt = ldt.withMinute(15);
        } else if (minutes >= 22 && minutes < 37) {
            ldt = ldt.withMinute(30);
        } else if (minutes >= 37 && minutes < 52) {
            ldt = ldt.withMinute(45);
        } else if (minutes >= 52) {
            ldt = ldt.plusMinutes((long)(60 - minutes));
        }

        instant = ldt.atZone(zone).toInstant();
        Date firstDate = Date.from(instant);
        return firstDate;
    }

    public static String getYesterday(String format) {
        LocalDate ld = LocalDate.now();
        ld = ld.minusDays(1L);
        ZoneId zonId = ZoneId.systemDefault();
        Instant instant = ld.atStartOfDay().atZone(zonId).toInstant();
        Date date = Date.from(instant);
        String yesterday = formatDate(date, format);
        return yesterday;
    }

    public static String getTodayStartTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        return dtf.format(new Date()).concat(" 00:00:00");
    }

    public static String getTodayEndTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        return dtf.format(new Date()).concat(" 23:59:59");
    }

    public static String getYesterdayStartTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        return dtf.format(add(new Date(), 5, -1)).concat(" 00:00:00");
    }

    public static String getYesterdayEndTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        return dtf.format(add(new Date(), 5, -1)).concat(" 23:59:59");
    }

    public static String getCurrentMonthStartTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, 1);
        return dtf.format(calendar.getTime()).concat(" 00:00:00");
    }

    public static String getCurrentMonthEndTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, calendar.getActualMaximum(5));
        return dtf.format(calendar.getTime()).concat(" 23:59:59");
    }

    public static String getLastMonthStartTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, -1);
        calendar.set(5, 1);
        return dtf.format(calendar.getTime()).concat(" 00:00:00");
    }

    public static String getLastMonthEndTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, 0);
        return dtf.format(calendar.getTime()).concat(" 23:59:59");
    }

    public static String getLastMonthEndTime(String time) throws ParseException {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = getDate(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 0);
        return dtf.format(calendar.getTime()).concat(" 23:59:59");
    }

    public static String getMonthStartTime(String time) throws ParseException {
        Date date = getDate(time);
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMinimum(5));
        return dtf.format(calendar.getTime()).concat(" 00:00:00");
    }

    public static String getMonthEndTime(String time) throws ParseException {
        Date date = getDate(time);
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMaximum(5));
        return dtf.format(calendar.getTime()).concat(" 23:59:59");
    }

    public static Long getDiffMinutes(Date start, Date end) {
        Instant startInt = start.toInstant();
        Instant endInt = end.toInstant();
        Duration duration = Duration.between(startInt, endInt);
        Long value = duration.toMinutes();
        return value;
    }

    public static Long getDiffMillis(Date start, Date end) {
        Instant startInt = start.toInstant();
        Instant endInt = end.toInstant();
        Duration duration = Duration.between(startInt, endInt);
        Long value = duration.toMillis();
        return value;
    }

    public static Long getDiffDays(Date start, Date end) {
        Date startDay = getFormateDate(start, "yyyy-MM-dd");
        Date endDay = getFormateDate(end, "yyyy-MM-dd");
        Instant startInt = startDay.toInstant();
        Instant endInt = endDay.toInstant();
        Duration duration = Duration.between(startInt, endInt);
        Long value = duration.toDays();
        return value;
    }

    public static int getDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getActualMaximum(5);
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(5);
    }

    public static int getDaysOfMonth(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate(date));
        return calendar.getActualMaximum(5);
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static int getDayOfMonth(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate(date));
        return calendar.get(5);
    }

    public static int getHourOfDay(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate(date));
        return calendar.get(11);
    }

    public static String getNextMonthStartTime() {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(5, 1);
        calendar.add(2, 1);
        return dtf.format(calendar.getTime()).concat(" 00:00:00");
    }

    public static String getNextMonthStartTime(String time) throws ParseException {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = getDate(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.add(2, 1);
        return dtf.format(calendar.getTime()).concat(" 00:00:00");
    }

    public static String getHourTime(Date date) {
        String time = formatDate(date, "yyyy-MM-dd HH:mm:ss");
        return time.substring(0, 14).concat("00:00");
    }

    public static String getHourMinuteTime(Date date) {
        String time = formatDate(date, "yyyy-MM-dd HH:mm:ss");
        return time.substring(0, 17).concat("00");
    }

    public static Float getHourDiff(Date start, Date end, int scale) {
        return DecimalUtil.divideAndRoundHalfUp(getDiffMillis(start, end).floatValue(), 3600000.0F, scale);
    }
}

