// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.util;

import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * The main utility class for schedule.
 * <li>Provide date/time convertion.<br>
 * <br>
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public class ExpressionUtil {

    private static Log log = LogFactory.getLog(ExpressionUtil.class);

    private static final String SECONDS = "SECONDS";

    private static final String MINUTES = "MINUTES";

    private static final String HOURS = "HOURS";

    private static final String DAY_OF_MONTH = "DAY_OF_MONTH";

    private static final String MONTH = "MONTH";

    private static final String DAY_OF_WEEK = "DAY_OF_WEEK";

    private static final String YEAR = "YEAR";

    private static final String Y3_QUARTS_CONF_PATH = "/y3_quartz.properties";

    private static Hashtable<String, String> cronTable = new Hashtable<String, String>();

    public static InputStream getQuartzConfig() {
        ClassLoader classLoader = ExpressionUtil.class
                .getClassLoader();

        InputStream config = classLoader
                .getResourceAsStream(Y3_QUARTS_CONF_PATH);

        if (null == config) {
            log.error("Can't load Quartz config with path :"
                    + Y3_QUARTS_CONF_PATH);
            throw new RuntimeException(
                    "Can't load Quartz config with path :"
                            + Y3_QUARTS_CONF_PATH);
        }

        return config;
    }

    /**
     * <p>
     * A "Cron-Expression" is a string comprised of 6 or 7 fields separated by
     * white space.
     * <p>
     * The 6 mandatory and 1 optional fields are as follows:<br>
     * Field Name Allowed Values Allowed Special Characters
     * <p>
     * <b>Seconds</b> 0-59 , - * /
     * <p>
     * <b>Minutes</b> 0-59 , - * /
     * <p>
     * <b>Hours</b> 0-23 , - * /
     * <p>
     * <b>Day-of-month</b> 1-31 , - * ? / L W C
     * <p>
     * <b>Month</b> 1-12 or JAN-DEC , - * /
     * <p>
     * <b>Day-of-Week</b> 1-7 or SUN-SAT , - * ? / L C #
     * <p>
     * <b>Year</b> (Optional) empty, 1970-2099 , - * /
     * <p>
     * <br>
     * <a
     * href="http://quartz.sourceforge.net/javadoc/org/quartz/CronTrigger.html">CronTriggers
     * Tutorial</a>
     * 
     * @param scheduleObj
     * @return
     */
    public static String convertToCronExpression(
            TaskScheduleDateTimeInfo scheduleObj) {
        // Re-initialise the Cron table
        cronTable.clear();

        // This job runs at PERIODICAL INTERVAL
        if ((null != scheduleObj.getJobPeriodInterval())
                && !"".equals(scheduleObj.getJobPeriodInterval().trim())) {

            cronTable.put(MINUTES, "*");
            cronTable.put(HOURS, "*");
            convertMinsToCronExp(scheduleObj.getJobPeriodInterval());
            cronTable.put(DAY_OF_MONTH, "*");
            cronTable.put(MONTH, "*");
            cronTable.put(DAY_OF_WEEK, "?");
        }
        // This is an AD HOC job
        else if ((null != scheduleObj.getJobAdhocRunDate())
                && !"".equals(scheduleObj.getJobAdhocRunDate().trim())) {
            convertAHMSToCronExp(scheduleObj.getJobAdhocRunInterval());

            String[] time = getTime(scheduleObj.getJobAdhocRunTime());

            cronTable.put(MINUTES, time[1]);
            cronTable.put(HOURS, time[0]);

            String[] date = getDate(scheduleObj.getJobAdhocRunDate());
            cronTable.put(DAY_OF_MONTH, date[2]);
            cronTable.put(MONTH, date[1]);
            cronTable.put(DAY_OF_WEEK, "?");
            cronTable.put(YEAR, date[0]);
        }
        // This is a DAILY job
        else if ((null != scheduleObj.getJobDailyTime())
                && !"".equals(scheduleObj.getJobDailyTime().trim())) {
            String[] time = getTime(scheduleObj.getJobDailyTime());
            cronTable.put(SECONDS, "0");
            cronTable.put(MINUTES, time[1]);
            cronTable.put(HOURS, time[0]);
            cronTable.put(DAY_OF_MONTH, "?");
            cronTable.put(MONTH, "*");
            cronTable.put(DAY_OF_WEEK, "*");
        }
        // This is a WEEKLY job
        else if ((null != scheduleObj.getJobWeeklyDate())
                && !"".equals(scheduleObj.getJobWeeklyDate().trim())) {
            String[] time = getTime(scheduleObj.getJobWeeklyTime());
            cronTable.put(SECONDS, "0");
            cronTable.put(MINUTES, time[1]);
            cronTable.put(HOURS, time[0]);
            cronTable.put(DAY_OF_MONTH, "?");
            cronTable.put(MONTH, "*");
            cronTable.put(DAY_OF_WEEK, scheduleObj.getJobWeeklyDate());
        }
        // This is a MONTHLY job
        else if ((null != scheduleObj.getJobMonthlyDate())
                && !"".equals(scheduleObj.getJobMonthlyDate().trim())) {

            String[] time = getTime(scheduleObj.getJobMonthlyTime());
            cronTable.put(SECONDS, "0");
            cronTable.put(MINUTES, time[1]);
            cronTable.put(HOURS, time[0]);
            cronTable.put(DAY_OF_MONTH, scheduleObj.getJobMonthlyDate());
            cronTable.put(MONTH, "*");
            cronTable.put(DAY_OF_WEEK, "?");
        }
        // This is a YEARLY job
        else if ((null != scheduleObj.getJobYearlyDate())
                && !"".equals(scheduleObj.getJobYearlyDate().trim())) {
            String[] time = getTime(scheduleObj.getJobYearlyTime());
            cronTable.put(SECONDS, "0");
            cronTable.put(MINUTES, time[1]);
            cronTable.put(HOURS, time[0]);
            cronTable.put(DAY_OF_MONTH, scheduleObj.getJobYearlyDate());
            cronTable.put(MONTH, scheduleObj.getJobYearlyMonth());
            cronTable.put(DAY_OF_WEEK, "?");
        }
        // Cron Expression: conversion not required
        else if ((null != scheduleObj.getJobCronExpression())
                && !"".equals(scheduleObj.getJobCronExpression())) {
            return scheduleObj.getJobCronExpression();
        }

        return convertTableToCronExp();
    }

//    public static String convertToCronExpression(
//    		TaskScheduleDateTimeInfo scheduleObj) {
//        // Re-initialise the Cron table
//        cronTable.clear();
//
//        // This job runs at PERIODICAL INTERVAL
//        if ((null != scheduleObj.getJobPeriodInterval())
//                && !"".equals(scheduleObj.getJobPeriodInterval().trim())) {
//
//            cronTable.put(MINUTES, "*");
//            cronTable.put(HOURS, "*");
//            convertMinsToCronExp(scheduleObj.getJobPeriodInterval());
//            cronTable.put(DAY_OF_MONTH, "*");
//            cronTable.put(MONTH, "*");
//            cronTable.put(DAY_OF_WEEK, "?");
//        }
//        // This is an AD HOC job
//        else if ((null != scheduleObj.getJobAdhocRunDate())
//                && !"".equals(scheduleObj.getJobAdhocRunDate().trim())) {
//            convertAHMSToCronExp(scheduleObj.getJobAdhocRunInterval());
//
//            String[] time = getTime(scheduleObj.getJobAdhocRunTime());
//
//            cronTable.put(MINUTES, time[1]);
//            cronTable.put(HOURS, time[0]);
//
//            String[] date = getDate(scheduleObj.getJobAdhocRunDate());
//            cronTable.put(DAY_OF_MONTH, date[2]);
//            cronTable.put(MONTH, date[1]);
//            cronTable.put(DAY_OF_WEEK, "?");
//            cronTable.put(YEAR, date[0]);
//        }
//        // This is a DAILY job
//        else if ((null != scheduleObj.getJobDailyTime())
//                && !"".equals(scheduleObj.getJobDailyTime().trim())) {
//            String[] time = getTime(scheduleObj.getJobDailyTime());
//            cronTable.put(SECONDS, "0");
//            cronTable.put(MINUTES, time[1]);
//            cronTable.put(HOURS, time[0]);
//            cronTable.put(DAY_OF_MONTH, "?");
//            cronTable.put(MONTH, "*");
//            cronTable.put(DAY_OF_WEEK, "*");
//        }
//        // This is a WEEKLY job
//        else if ((null != scheduleObj.getJobWeeklyDate())
//                && !"".equals(scheduleObj.getJobWeeklyDate().trim())) {
//            String[] time = getTime(scheduleObj.getJobWeeklyTime());
//            cronTable.put(SECONDS, "0");
//            cronTable.put(MINUTES, time[1]);
//            cronTable.put(HOURS, time[0]);
//            cronTable.put(DAY_OF_MONTH, "?");
//            cronTable.put(MONTH, "*");
//            cronTable.put(DAY_OF_WEEK, scheduleObj.getJobWeeklyDate());
//        }
//        // This is a MONTHLY job
//        else if ((null != scheduleObj.getJobMonthlyDate())
//                && !"".equals(scheduleObj.getJobMonthlyDate().trim())) {
//
//            String[] time = getTime(scheduleObj.getJobMonthlyTime());
//            cronTable.put(SECONDS, "0");
//            cronTable.put(MINUTES, time[1]);
//            cronTable.put(HOURS, time[0]);
//            cronTable.put(DAY_OF_MONTH, scheduleObj.getJobMonthlyDate());
//            cronTable.put(MONTH, "*");
//            cronTable.put(DAY_OF_WEEK, "?");
//        }
//        // This is a YEARLY job
//        else if ((null != scheduleObj.getJobYearlyDate())
//                && !"".equals(scheduleObj.getJobYearlyDate().trim())) {
//            String[] time = getTime(scheduleObj.getJobYearlyTime());
//            cronTable.put(SECONDS, "0");
//            cronTable.put(MINUTES, time[1]);
//            cronTable.put(HOURS, time[0]);
//            cronTable.put(DAY_OF_MONTH, scheduleObj.getJobYearlyDate());
//            cronTable.put(MONTH, scheduleObj.getJobYearlyMonth());
//            cronTable.put(DAY_OF_WEEK, "?");
//        }
//        // Cron Expression: conversion not required
//        else if ((null != scheduleObj.getJobCronExpression())
//                && !"".equals(scheduleObj.getJobCronExpression())) {
//            return scheduleObj.getJobCronExpression();
//        }
//
//        return convertTableToCronExp();
//    }

//    public static Date convertToDate(String date, String time,
//            String datePattern) throws ParseException {
//
//        Calendar calendar = new GregorianCalendar();
//        String[] timeArray = getTime(time);
//        calendar.setTime(DateUtil.convertStringToDate(datePattern, date));
//        calendar.set(Calendar.HOUR, Integer.parseInt(timeArray[0]));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
//
//        return new Date(calendar.getTime().getTime());
//    }

    public static Date convertToDateTime(java.util.Date date, String time) throws ParseException {

        Calendar calendar = new GregorianCalendar();
        String[] timeArray = getTime(time);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));

        return new Date(calendar.getTime().getTime());
    }
    
//    public static Timestamp convertToTimestamp(String date, String time,
//            String datePattern) throws ParseException {
//        Date formattedDate = convertToDate(date, time, datePattern);
//
//        return new Timestamp(formattedDate.getTime());
//    }

//    public static Calendar convertToCalendar(String date, String time,
//            String datePattern) throws ParseException {
//        Calendar tmp = Calendar.getInstance();
//        tmp.setTime(convertToDate(date, time, datePattern));
//
//        return tmp;
//    }

    private static Hashtable<String,String> convertAHMSToCronExp(String milliSeconds) {
        int totalSec = Integer.parseInt(milliSeconds) / 1000;
        cronTable.put(SECONDS, String.valueOf(totalSec));
        return cronTable;
    }

    private static Hashtable<String,String> convertMinsToCronExp(String minutes) {
        int sec = 0;
        int mins = Integer.parseInt(minutes);
        int hrs = 0;

        if (mins > 59) {
            hrs = (int) (mins / 60);
            mins = mins - (hrs * 60);
        }

        cronTable.put(SECONDS, String.valueOf(sec));
        if (mins > 0) {
            cronTable.put(MINUTES, "/" + String.valueOf(mins));
        }
        if (hrs > 0) {
            cronTable.put(HOURS, "/" + String.valueOf(hrs));
        }
        return cronTable;
    }

    
    private static String[] getDate(String date) {
        String[] dateArray = new String[3];
        StringTokenizer tc = new StringTokenizer(date, "-");

        // Year
        dateArray[0] = tc.nextToken();

        // Month
        dateArray[1] = tc.nextToken();

        // Day
        dateArray[2] = tc.nextToken();

        return dateArray;
    }

    
    private static String[] getTime(String time) {
        String[] timeArray = new String[2];
        StringTokenizer tc = new StringTokenizer(time, ":");

        // Hour
        timeArray[0] = tc.nextToken();

        // Minute
        timeArray[1] = tc.nextToken();

        return timeArray;
    }

    
    private static String convertTableToCronExp() {
        StringBuffer cronExpression = new StringBuffer();
        cronExpression.append(cronTable.get(SECONDS));
        cronExpression.append(" ");
        cronExpression.append(cronTable.get(MINUTES));
        cronExpression.append(" ");
        cronExpression.append(cronTable.get(HOURS));
        cronExpression.append(" ");
        cronExpression.append(cronTable.get(DAY_OF_MONTH));
        cronExpression.append(" ");
        cronExpression.append(cronTable.get(MONTH));
        cronExpression.append(" ");
        cronExpression.append(cronTable.get(DAY_OF_WEEK));

        if (null != cronTable.get(YEAR)) {
            cronExpression.append(" ");
            cronTable.get(YEAR);
        }

        return cronExpression.toString();
    }
}
