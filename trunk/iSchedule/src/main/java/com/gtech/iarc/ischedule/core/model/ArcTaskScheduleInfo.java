// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;


/**
 * 
 * @author ZHIDAO
 * @revision $Id: ArcTaskScheduleInfo.java 6647 2007-08-21 15:11:13Z zhidao $
 */
public class ArcTaskScheduleInfo implements IArcTaskScheduleInfo {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2337782551524178002L;
	private String jobPeriodInterval;
    private String jobAdhocRunDate;
    private String jobAdhocRunTime;
    private String jobAdhocRunInterval;
    private String jobDailyTime;
    private String jobWeeklyDate;
    private String jobWeeklyTime;
    private String jobMonthlyDate;
    private String jobMonthlyTime;
    private String jobYearlyDate;
    private String jobYearlyMonth;
    private String jobYearlyTime;
    private String jobCronExpression;
    private String jobRadio1;

    /**
    * @return Returns the jobAdhocRunDate.
    */
    public String getJobAdhocRunDate() {
        return jobAdhocRunDate;
    }

    /**
     * @return Returns the jobAdhocRunInterval.
     */
    public String getJobAdhocRunInterval() {
        return jobAdhocRunInterval;
    }

    /**
     * @return Returns the jobAdhocRunTime.
     */
    public String getJobAdhocRunTime() {
        return jobAdhocRunTime;
    }

    /**
     * @return Returns the jobCronExpression.
     */
    public String getJobCronExpression() {
        return jobCronExpression;
    }

    /**
     * @return Returns the jobDailyTime.
     */
    public String getJobDailyTime() {
        return jobDailyTime;
    }

    /**
     * @return Returns the jobMonthlyDate.
     */
    public String getJobMonthlyDate() {
        return jobMonthlyDate;
    }

    /**
     * @return Returns the jobMonthlyTime.
     */
    public String getJobMonthlyTime() {
        return jobMonthlyTime;
    }

    /**
     * @return Returns the jobPeriodInterval.
     */
    public String getJobPeriodInterval() {
        return jobPeriodInterval;
    }

    /**
     * @return Returns the jobRadio1.
     */
    public String getJobRadio1() {
        return jobRadio1;
    }

    /**
     * @return Returns the jobWeeklyDate.
     */
    public String getJobWeeklyDate() {
        return jobWeeklyDate;
    }

    /**
     * @return Returns the jobWeeklyTime.
     */
    public String getJobWeeklyTime() {
        return jobWeeklyTime;
    }

    /**
     * @return Returns the jobYearlyDate.
     */
    public String getJobYearlyDate() {
        return jobYearlyDate;
    }

    /**
     * @return Returns the jobYearlyMonth.
     */
    public String getJobYearlyMonth() {
        return jobYearlyMonth;
    }

    /**
     * @return Returns the jobYearlyTime.
     */
    public String getJobYearlyTime() {
        return jobYearlyTime;
    }

    /**
     * @param jobAdhocRunDate The jobAdhocRunDate to set.
     */
    public void setJobAdhocRunDate(String jobAdhocRunDate) {
        this.jobAdhocRunDate = jobAdhocRunDate;
    }

    /**
     * @param jobAdhocRunInterval The jobAdhocRunInterval to set.
     */
    public void setJobAdhocRunInterval(String jobAdhocRunInterval) {
        this.jobAdhocRunInterval = jobAdhocRunInterval;
    }

    /**
     * @param jobAdhocRunTime The jobAdhocRunTime to set.
     */
    public void setJobAdhocRunTime(String jobAdhocRunTime) {
        this.jobAdhocRunTime = jobAdhocRunTime;
    }

    /**
     * @param jobCronExpression The jobCronExpression to set.
     */
    public void setJobCronExpression(String jobCronExpression) {
        this.jobCronExpression = jobCronExpression;
    }

    /**
     * @param jobDailyTime The jobDailyTime to set.
     */
    public void setJobDailyTime(String jobDailyTime) {
        this.jobDailyTime = jobDailyTime;
    }

    /**
     * @param jobMonthlyDate The jobMonthlyDate to set.
     */
    public void setJobMonthlyDate(String jobMonthlyDate) {
        this.jobMonthlyDate = jobMonthlyDate;
    }

    /**
     * @param jobMonthlyTime The jobMonthlyTime to set.
     */
    public void setJobMonthlyTime(String jobMonthlyTime) {
        this.jobMonthlyTime = jobMonthlyTime;
    }

    /**
     * @param jobPeriodInterval The jobPeriodInterval to set.
     */
    public void setJobPeriodInterval(String jobPeriodInterval) {
        this.jobPeriodInterval = jobPeriodInterval;
    }

    /**
     * @param jobRadio1 The jobRadio1 to set.
     */
    public void setJobRadio1(String jobRadio1) {
        this.jobRadio1 = jobRadio1;
    }

    /**
     * @param jobWeeklyDate The jobWeeklyDate to set.
     */
    public void setJobWeeklyDate(String jobWeeklyDate) {
        this.jobWeeklyDate = jobWeeklyDate;
    }

    /**
     * @param jobWeeklyTime The jobWeeklyTime to set.
     */
    public void setJobWeeklyTime(String jobWeeklyTime) {
        this.jobWeeklyTime = jobWeeklyTime;
    }

    /**
     * @param jobYearlyDate The jobYearlyDate to set.
     */
    public void setJobYearlyDate(String jobYearlyDate) {
        this.jobYearlyDate = jobYearlyDate;
    }

    /**
     * @param jobYearlyMonth The jobYearlyMonth to set.
     */
    public void setJobYearlyMonth(String jobYearlyMonth) {
        this.jobYearlyMonth = jobYearlyMonth;
    }

    /**
     * @param jobYearlyTime The jobYearlyTime to set.
     */
    public void setJobYearlyTime(String jobYearlyTime) {
        this.jobYearlyTime = jobYearlyTime;
    }
}
