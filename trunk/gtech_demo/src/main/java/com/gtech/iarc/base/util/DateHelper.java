package com.gtech.iarc.base.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Locale;
import java.util.TimeZone;

/** Yet another date utility lying around.
 * @author $author$	$Revision: 1.1 $
 */
public class DateHelper {
    private static Log log = LogFactory.getLog(DateHelper.class);
    private static final String DATE_SEPARATOR_HYPEN = "-";
    private static final String DATE_SEPARATOR_FORWARD_SLASH = "/";

    /** Non-instantiate class. */
    private DateHelper() {}

    public static java.sql.Date getCurrentDate() {
        return new java.sql.Date(new java.util.Date().getTime());
    }
    
    /** Removed all the "-" and "/" in the given pattern.
     * @param pattern the given pattern
     * @return the raw pattern
     */
    public static String getRawPattern(String pattern) {
        pattern = pattern.replaceAll(DATE_SEPARATOR_HYPEN, "");
        pattern = pattern.replaceAll(DATE_SEPARATOR_FORWARD_SLASH, "");
        return pattern;
    }

    /** Format the date to the given <tt>timezone</tt> and format it into the date 
     * <tt>pattern</tt>.
     * @param date the given <code>java.sql.Date</code>.
     * @param timezone the given time zone.
     * @param locale the given locale.
     * @param pattern the given date pattern.
     *
     * @return the formatted date string of the given time zone, locale and date pattern
     */
    public static String getShortDate(java.sql.Date date, TimeZone timezone, Locale locale, String pattern) {
        return getDisplayDateByPattern(date, timezone, locale, pattern);
    }

    /** Overloaded version of <code>java.util.Date</code>. */
    public static String getShortDate(java.util.Date date, TimeZone timezone, Locale locale, String pattern) {
        return getDisplayDateByPattern(date, timezone, locale, pattern);
    }
    
    private static String getDisplayDateByPattern(java.util.Date date, TimeZone timezone, Locale locale, String pattern) {
        if (null == timezone) {
            timezone = TimeZone.getDefault();
        }
        if (null == locale) {
            locale = Locale.getDefault();
        }
        
        // Convert to the given timezone 
        java.util.Date dt = DateUtil.convertToTimeZone(date, timezone);
        
        // Reformat to the given pattern
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(pattern);
        return dateFormat.format(dt);
    }

    private static String getDisplayDateByPattern(java.sql.Date date, TimeZone timezone, Locale locale, String pattern) {
        if (null == timezone) {
            timezone = TimeZone.getDefault();
        }
        if (null == locale) {
            locale = Locale.getDefault();
        }
        
        // Convert to the given timezone 
        java.util.Date dt = DateUtil.convertToTimeZone(date, timezone);
        
        // Reformat to the given pattern
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(pattern);
        return dateFormat.format(dt);
    }
}