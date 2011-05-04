// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

/**
 * 
 * @author ZHIDAO
 * @revision $Id: IArcTaskScheduleInfo.java 6647 2007-08-21 15:11:13Z zhidao $
 */
public interface TaskScheduleDateTimeInfo{

	/**
	 * @return Returns the jobAdhocRunDate.
	 */
	public String getJobAdhocRunDate();

	/**
	 * @return Returns the jobAdhocRunInterval.
	 */
	public String getJobAdhocRunInterval();

	/**
	 * @return Returns the jobAdhocRunTime.
	 */
	public String getJobAdhocRunTime();

	/**
	 * @return Returns the jobCronExpression.
	 */
	public String getJobCronExpression();

	/**
	 * @return Returns the jobDailyTime.
	 */
	public String getJobDailyTime();

	/**
	 * @return Returns the jobMonthlyDate.
	 */
	public String getJobMonthlyDate();

	/**
	 * @return Returns the jobMonthlyTime.
	 */
	public String getJobMonthlyTime();

	/**
	 * @return Returns the jobPeriodInterval.
	 */
	public String getJobPeriodInterval();

	/**
	 * @return Returns the jobRadio1.
	 */
	public String getJobRadio1();

	/**
	 * @return Returns the jobWeeklyDate.
	 */
	public String getJobWeeklyDate();

	/**
	 * @return Returns the jobWeeklyTime.
	 */
	public String getJobWeeklyTime();

	/**
	 * @return Returns the jobYearlyDate.
	 */
	public String getJobYearlyDate();

	/**
	 * @return Returns the jobYearlyMonth.
	 */
	public String getJobYearlyMonth();

	/**
	 * @return Returns the jobYearlyTime.
	 */
	public String getJobYearlyTime();

	/**
	 * @param jobAdhocRunDate The jobAdhocRunDate to set.
	 */
	public void setJobAdhocRunDate(String jobAdhocRunDate);

	/**
	 * @param jobAdhocRunInterval The jobAdhocRunInterval to set.
	 */
	public void setJobAdhocRunInterval(String jobAdhocRunInterval);

	/**
	 * @param jobAdhocRunTime The jobAdhocRunTime to set.
	 */
	public void setJobAdhocRunTime(String jobAdhocRunTime);

	/**
	 * @param jobCronExpression The jobCronExpression to set.
	 */
	public void setJobCronExpression(String jobCronExpression);

	/**
	 * @param jobDailyTime The jobDailyTime to set.
	 */
	public void setJobDailyTime(String jobDailyTime);

	/**
	 * @param jobMonthlyDate The jobMonthlyDate to set.
	 */
	public void setJobMonthlyDate(String jobMonthlyDate);

	/**
	 * @param jobMonthlyTime The jobMonthlyTime to set.
	 */
	public void setJobMonthlyTime(String jobMonthlyTime);

	/**
	 * @param jobPeriodInterval The jobPeriodInterval to set.
	 */
	public void setJobPeriodInterval(String jobPeriodInterval);

	/**
	 * @param jobRadio1 The jobRadio1 to set.
	 */
	public void setJobRadio1(String jobRadio1);

	/**
	 * @param jobWeeklyDate The jobWeeklyDate to set.
	 */
	public void setJobWeeklyDate(String jobWeeklyDate);

	/**
	 * @param jobWeeklyTime The jobWeeklyTime to set.
	 */
	public void setJobWeeklyTime(String jobWeeklyTime);

	/**
	 * @param jobYearlyDate The jobYearlyDate to set.
	 */
	public void setJobYearlyDate(String jobYearlyDate);

	/**
	 * @param jobYearlyMonth The jobYearlyMonth to set.
	 */
	public void setJobYearlyMonth(String jobYearlyMonth);

	/**
	 * @param jobYearlyTime The jobYearlyTime to set.
	 */
	public void setJobYearlyTime(String jobYearlyTime);

}