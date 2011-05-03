// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;



/**
 * 
 * @author ZHIDAO
 * @revision $Id: ArcTaskScheduleInfo.java 6647 2007-08-21 15:11:13Z zhidao $
 */
public class DefaultTaskScheduleDateTimeInfo implements TaskScheduleDateTimeInfo {

	
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
    private String createdBy;
    private String modifiedBy;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    
	public String getJobPeriodInterval() {
		return jobPeriodInterval;
	}
	public void setJobPeriodInterval(String jobPeriodInterval) {
		this.jobPeriodInterval = jobPeriodInterval;
	}
	public String getJobAdhocRunDate() {
		return jobAdhocRunDate;
	}
	public void setJobAdhocRunDate(String jobAdhocRunDate) {
		this.jobAdhocRunDate = jobAdhocRunDate;
	}
	public String getJobAdhocRunTime() {
		return jobAdhocRunTime;
	}
	public void setJobAdhocRunTime(String jobAdhocRunTime) {
		this.jobAdhocRunTime = jobAdhocRunTime;
	}
	public String getJobAdhocRunInterval() {
		return jobAdhocRunInterval;
	}
	public void setJobAdhocRunInterval(String jobAdhocRunInterval) {
		this.jobAdhocRunInterval = jobAdhocRunInterval;
	}
	public String getJobDailyTime() {
		return jobDailyTime;
	}
	public void setJobDailyTime(String jobDailyTime) {
		this.jobDailyTime = jobDailyTime;
	}
	public String getJobWeeklyDate() {
		return jobWeeklyDate;
	}
	public void setJobWeeklyDate(String jobWeeklyDate) {
		this.jobWeeklyDate = jobWeeklyDate;
	}
	public String getJobWeeklyTime() {
		return jobWeeklyTime;
	}
	public void setJobWeeklyTime(String jobWeeklyTime) {
		this.jobWeeklyTime = jobWeeklyTime;
	}
	public String getJobMonthlyDate() {
		return jobMonthlyDate;
	}
	public void setJobMonthlyDate(String jobMonthlyDate) {
		this.jobMonthlyDate = jobMonthlyDate;
	}
	public String getJobMonthlyTime() {
		return jobMonthlyTime;
	}
	public void setJobMonthlyTime(String jobMonthlyTime) {
		this.jobMonthlyTime = jobMonthlyTime;
	}
	public String getJobYearlyDate() {
		return jobYearlyDate;
	}
	public void setJobYearlyDate(String jobYearlyDate) {
		this.jobYearlyDate = jobYearlyDate;
	}
	public String getJobYearlyMonth() {
		return jobYearlyMonth;
	}
	public void setJobYearlyMonth(String jobYearlyMonth) {
		this.jobYearlyMonth = jobYearlyMonth;
	}
	public String getJobYearlyTime() {
		return jobYearlyTime;
	}
	public void setJobYearlyTime(String jobYearlyTime) {
		this.jobYearlyTime = jobYearlyTime;
	}
	public String getJobCronExpression() {
		return jobCronExpression;
	}
	public void setJobCronExpression(String jobCronExpression) {
		this.jobCronExpression = jobCronExpression;
	}
	public String getJobRadio1() {
		return jobRadio1;
	}
	public void setJobRadio1(String jobRadio1) {
		this.jobRadio1 = jobRadio1;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
    
}
