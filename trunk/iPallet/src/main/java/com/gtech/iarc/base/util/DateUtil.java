package com.gtech.iarc.base.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Date utility class.
 * @version	$Id: DateUtil.java 7761 2007-10-19 03:30:29Z walmart $
 */
public class DateUtil {
    private final static String timePattern = "(\\d{2})([:\\.-]?)(\\d{0,2})([:\\.-]?)(\\d{0,2})\\s*(am|Am|aM|AM|pm|Pm|pM|PM)?";
    public final static String datePattern = "dd-MM-yyyy";
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static Date NULL_DATE = new Date(0);

    /**
     * DOCUMENT ME!
     *
     * @param strTime DOCUMENT ME!
     * @param zone DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ParseException DOCUMENT ME!
     */
    public static Date parseTime(String strTime, TimeZone zone)
        throws ParseException {
        /** time string formats that are supported are
        * 1. hhmmss
        * 2. hhmm
        * 3. hh:mm:ss
        * 4. hh:mm
        * 5. hh-mm-ss
        * 6. hh-mm
        */
        if ((strTime == null) || (strTime.length() == 0)) {
            return null;
        }

        strTime = strTime.trim().replaceAll(" ", "");

        DateFormat df = null;
        df = getTimeFormat(strTime);

        df.setTimeZone(zone);

        return df.parse(strTime);
    }

    private static DateFormat getTimeFormat(String strTime) {
        DateFormat df = null;
        Pattern p = Pattern.compile(timePattern);
        String pt = null;

        Matcher m = p.matcher(strTime);

        if (m.matches()) {
            pt = "hh";

            if ((m.group(3) != null) && !m.group(3).equals("")) {
                pt += (m.group(2).trim() + "mm");
            }

            if ((m.group(5) != null) && !m.group(5).equals("")) {
                pt += (m.group(2) + "ss");
            }

            if ((m.group(6) != null) && !m.group(6).equals("")) {
                pt += "a";
            }

            df = new SimpleDateFormat(pt);
        }

        return df;
    }

    /**
     * DOCUMENT ME!
     *
     * @param strDate DOCUMENT ME!
     * @param zone DOCUMENT ME!
     * @param locale DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ParseException DOCUMENT ME!
     */
    public static Date parseDate(String strDate, TimeZone zone, Locale locale)
        throws ParseException {
        /** Date string formats that are supported are
        *  0. dd/mm/yy
        *  1. dd/mm/yyyy
        *  2. yyyymmdd
        *  3. yymmdd
        *  4. yyyy.mm.dd
        *  5. yy.mm.dd
        *  6. yyyy-MM-dd
        *  7. yy-MM-dd
        */
        if ((strDate == null) || (strDate.length() == 0)) {
            return null;
        }

        strDate = strDate.trim();

        DateFormat df = null;

        if (locale != null) {
            try {
                df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
                df.setTimeZone(zone);
                df.setLenient(false);

                Date d = null;
                d = df.parse(strDate);

                if (d != null) {
                    return d;
                }
            } catch (Exception e) {
            }
        } else {
            try {
                df = DateFormat.getDateInstance(DateFormat.SHORT,
                        Locale.getDefault());
                df.setTimeZone(zone);
                df.setLenient(false);

                Date d = null;
                d = df.parse(strDate);

                if (d != null) {
                    return d;
                }
            } catch (Exception e) {
                ;
            }
        }

        df = getDateFormat(strDate, "/", false); //try 0 and 1

        if (df == null) {
            df = getDateFormat(strDate, ".", true); //try 4 and 5
        }

        if (df == null) {
            df = getDateFormat(strDate, "-", true); //try 6 and 7
        }

        if (df == null) {
            df = getDateFormat(strDate, "", true);
        }

        df.setTimeZone(zone);
        df.setLenient(false);

        return df.parse(strDate);
    }

    public static DateFormat getDateFormat(String strDate, String deli, boolean isYearFront) {
        DateFormat df = null;

        if (deli.equals("")) {
            if (strDate.length() == 8) { //format 2
                df = new SimpleDateFormat("yyyyMMdd");
            } else if (strDate.length() == 6) { //format 3
                df = new SimpleDateFormat("yyMMdd");
            } else if (strDate.length() == 28) { //format 28
                df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            } else {
                df = DateFormat.getDateInstance(DateFormat.SHORT);
            }

            return df;
        }

        int firstDeli = strDate.indexOf(deli);
        int secondDeli = 0;

        if (firstDeli > 0) {
            secondDeli = strDate.indexOf(deli, firstDeli + 1);

            if (secondDeli > 0) {
                if (!isYearFront) {
                    if (strDate.substring(secondDeli + 1).length() == 4) {
                        df = new SimpleDateFormat("dd" + deli + "MM" + deli +
                                "yyyy");
                    } else if (strDate.substring(secondDeli + 1).length() == 2) {
                        df = new SimpleDateFormat("dd" + deli + "MM" + deli +
                                "yy");
                    } else {
                        ;
                    }
                } else if (strDate.substring(0, firstDeli).length() == 4) {
                    df = new SimpleDateFormat("yyyy" + deli + "MM" + deli +
                            "dd");
                } else if (strDate.substring(0, firstDeli).length() == 2) {
                    df = new SimpleDateFormat("yy" + deli + "MM" + deli + "dd");
                } else {
                    ;
                }
            }
        }

        return df;
    }

    public static String formatDate(Date aDate, TimeZone zone, Locale locale) {
        /** output date according to the locale and zone
        */
        if (false && log.isDebugEnabled()) {
            log.debug("In formatDate....");
            log.debug("Time Zone: " + zone.getDisplayName());
            log.debug("Locale: " + locale.getDisplayName());
        }

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        df.setTimeZone(zone);

        return df.format(aDate);
    }

    public static String formatTime(Date time, TimeZone zone, Locale locale) {
        /** output time according to the locale and zone
        */
        DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG, locale);
        df.setTimeZone(zone);

        return df.format(time);
    }

    /**
     * Return default datePattern (MM/dd/yyyy)
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        return datePattern;
    }

    /** Get the locale date pattern.
     * @return a string representing the date pattern on the UI.
     */
    public final static String getDatePattern(TimeZone timeZone, Locale locale) {
        if (null == timeZone) {
            timeZone = TimeZone.getDefault();
        }

        if (null == locale) {
            locale = Locale.getDefault();
        }

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        dateFormat.setTimeZone(timeZone);

        if (SimpleDateFormat.class.isAssignableFrom(dateFormat.getClass())) {
            return ((SimpleDateFormat) dateFormat).toPattern();
        } else {
            return datePattern;
        }
    }


    /** Convert the given <tt>strDate</tt> in the given <tt>datePattern</tt> represention. 
     */
    public static final Date convertStringToDate(String datePattern, String strDate) throws ParseException {
        if (org.apache.commons.lang.StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        return formatter.parse(strDate);
    }

    /** Convert the given <tt>Date</tt> that in default server timezone to the given <tt>timeZone</tt>. */ 
    public static final Date convertToTimeZone(Date date, TimeZone timeZone) {
        GregorianCalendar gc = new GregorianCalendar(timeZone);
        gc.setTime(date);
        
        Calendar cal = Calendar.getInstance();
        cal.set(gc.get(Calendar.YEAR), (gc.get(Calendar.MONTH)), gc.get(Calendar.DAY_OF_MONTH),
        	    gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE), gc.get(Calendar.SECOND));
        return cal.getTime();
    }
    
    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @param timeZone the user's time zone
     *
     * @return a converted Date object
     * @see java.text.SimpleDateFormat
     * @throws ParseException
     *
     * @throws ParseException DOCUMENT ME!
     */
    public static final Date convertStringToDate(String aMask, String strDate,
        TimeZone timeZone, Locale locale) throws ParseException {
        if (timeZone != null) {
            return parseDate(strDate, timeZone, locale);
        } else {
            return parseDate(strDate, TimeZone.getDefault(), locale);
        }
    }


    public static java.util.Date getTodayDate() {
        return new java.util.Date();
    }
    
    public static long getTodayDateInMillisInSeconds() {
        return getTodayDate().getTime();
    }
    
    /**
     * This method returns the current date in the format: MM/dd/yyyy
     *
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method returns the current date
     *
     * @param timeZone DOCUMENT ME!
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday(TimeZone timeZone)
        throws ParseException {
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(timeZone);

        return cal;
    }


    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @param timeZone the time zone
     * @param locale the locale
     *
     * @return a formatted string representation of the date
     */
    public static final String getDateTime(String aMask, Date aDate,
        TimeZone timeZone, Locale locale) {
        if (null == timeZone) {
            timeZone = TimeZone.getDefault();
        }

        if (null == locale) {
            locale = Locale.getDefault();
        }

        return formatDate(aDate, timeZone, locale);
    }


    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(Date aDate,
        TimeZone timeZone, Locale locale) {
        return getDateTime(datePattern, aDate, timeZone, locale);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     *
     * @throws ParseException
     * @deprecated Do not use this method. Use the method convertStringToDate(String, TimeZone, Locale)
     */
    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate = null;

        try {
            aDate = convertStringToDate(datePattern, strDate);
        } catch (ParseException pe) {
            /*
        	log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            pe.printStackTrace();
            */
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     *
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate, TimeZone timeZone,
        Locale locale) throws ParseException {
        Date aDate = null;

        try {
            aDate = convertStringToDate(datePattern, strDate, timeZone, locale);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate +
                "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }

    /** Convenient utility method dealing with date to add additional days to the
     * given date.
     * @param date that will be used for the days bumping.
     * @param days to be added/deducted.
     * @return Date that has been bumped up if days is positive, otherwise it'll be
     *         bumped down if negative.
     */
    public static Date bumpUpDate(Date date, int days) {
    	if (date == null) return null;
    	
        java.util.Calendar cal = dateToCal(date);
        java.util.GregorianCalendar gc = new java.util.GregorianCalendar(
            cal.get(java.util.Calendar.YEAR),
            cal.get(java.util.Calendar.MONTH),
            cal.get(java.util.Calendar.DATE));
        gc.add(java.util.GregorianCalendar.DATE, days);
        return gc.getTime();
    }
    
    /** Convenient utility method dealing with date to add additional months to the
     * given date.
     * @param date that will be used for the days bumping.
     * @param days to be added/deducted.
     * @return Date that has been bumped up if days is positive, otherwise it'll be
     *         bumped down if negative.
     */
    public static Date bumpUpMonth(Date date, int mtds) {
    	if (date == null) return null;
    	
        java.util.Calendar cal = dateToCal(date);
        java.util.GregorianCalendar gc = new java.util.GregorianCalendar(
            cal.get(java.util.Calendar.YEAR),
            cal.get(java.util.Calendar.MONTH),
            cal.get(java.util.Calendar.DATE));
        gc.add(java.util.GregorianCalendar.MONTH, mtds);
        return gc.getTime();
    }

    /** Convenient utility method dealing with getting the different of days between
     * two given dates, <tt>date1</tt> and <tt>date2</tt>. The date comparison ignores
     * the time (hh:mm:ss) in the date.
     * @param date1 to be compared.
     * @param date2 to be compared.
     * @return
     */
    public static int differentInDays(Date date1, Date date2) {
        if ((date1 != null) && (date2 != null)) {
            java.util.Calendar cal1 = java.util.Calendar.getInstance();
            cal1.setTime(clearDate(date1));

            java.util.Calendar cal2 = java.util.Calendar.getInstance();
            cal2.setTime(clearDate(date2));

            final long msPerDay = 1000 * 60 * 60 * 24;

            final long date1Milliseconds = cal1.getTime().getTime();
            final long date2Milliseconds = cal2.getTime().getTime();
            final int result = (int) Math.abs((date1Milliseconds -
                    date2Milliseconds) / msPerDay);

            return result;
        } else {
            return 0;
        }
    }

    /** Yet another convenient utility method to clear date of hour, min, sec and
     * millisec.
     */
    public static Date clearDate(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);

        java.util.GregorianCalendar gc = new java.util.GregorianCalendar(cal.get(
                    java.util.Calendar.YEAR),
                cal.get(java.util.Calendar.MONTH),
                cal.get(java.util.Calendar.DATE));

        return gc.getTime();
    }

    /** Check if the given <tt>date</tt> is null. */
    public static boolean isNull(Date date) {
        if ((date == null) || date.equals(NULL_DATE)) {
            return true;
        }

        return false;
    }

    public static java.util.Date convertToUtilDate(java.sql.Date date) {
        if (date != null && date instanceof java.sql.Date) {
            return new java.util.Date(date.getTime());
        }

        return null;
    }

    public static java.sql.Date convertToSqlDate(java.util.Date date) {
        if (date != null && date instanceof java.util.Date) {
            return new java.sql.Date(date.getTime());
        }

        return null;
    }

    private static java.util.Calendar dateToCal(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    public static String getDateToStringFormat(Date date, String format) {
        if (format == null) {
            // Use default
            format = getDatePattern();
        }
        
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }
}