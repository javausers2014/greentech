// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.JobBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.JobKey;

/**
 * Proxy Class for <b>org.quartz.Scheduler</b><br>
 * All schedule tasks' execution will be handled by Quartz framework.
 * 
 * @author ZHIDAO
 */
public class SchedulerControl {

	private static Log log = LogFactory.getLog(SchedulerControl.class);
	private Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * Manually start shceduler
	 * 
	 */
	public void start() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
			log.error("Failed in starting Scheduler: " + e.getMessage());
			throw new RuntimeException(e.getCause());
		}
	}

	/**
	 * Start the schedule job using Quartz.
	 */
	public void activateJob(Class <? extends Job> cls, String jobCode,
			String jobGroup, Date startTime, Date endTime, String cronExpression)
			throws SchedulerException, ParseException {

		JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobCode, jobGroup)
	    .storeDurably()
	    .requestRecovery()
	    .build();
//		quartzJobDetail.setName(jobCode);
//		quartzJobDetail.setGroup(jobGroup);
//		quartzJobDetail.setJobClass(cls);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobCode, jobGroup).forJob(jobDetail).startAt(startTime).endAt(endTime)
		.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

		
		scheduler.scheduleJob(jobDetail, trigger);
		log.debug("\n Quartz Cron Trigger created for Task[" + jobCode
				+ "], Group[" + jobGroup + "]\n");

	}

	/**
	 * Cancel the excution of schedule task from Quartz
	 */
	public void deleteJob(String jobCode, String jobGroup)
			throws SchedulerException {

		scheduler.deleteJob(JobKey.jobKey(jobCode,jobGroup));
		
		log.info("deleteJob - Successfully remove the job from Quartz");
	}

	/**
	 * Suspend/Pause active schedule task from Quartz
	 * 
	 * @param job
	 */
	public void pauseJob(String jobCode, String jobGroup)
			throws SchedulerException {

		scheduler.pauseTrigger(TriggerKey.triggerKey(jobCode, jobGroup));
		scheduler.pauseJob(JobKey.jobKey(jobCode,jobGroup));

	}

	/**
	 * Resume paused schedule task from Quartz
	 * 
	 * @param job
	 */
	public void resumeJob(String jobCode, String jobGroup)
			throws SchedulerException {

		scheduler.resumeJob(JobKey.jobKey(jobCode,jobGroup));

	}

	/**
	 * Resschedule existing task from Quartz, regardless previous status of the
	 * task.
	 * 
	 * @param job
	 */
	public void rescheduleJob(String jobCode,
			String jobGroup, Date startTime, Date endTime, String cronExpression)
			throws SchedulerException, ParseException {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobCode, jobGroup);
		Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(jobCode, jobGroup).forJob(jobCode,jobGroup).startAt(startTime).endAt(endTime)
		.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

		scheduler.rescheduleJob(triggerKey, newTrigger) ;
	}

	/**
	 * API only used for application initialization. Once scheduler is shut
	 * down, it can't be loaded maunally, the app must be restarted to reload
	 * the scheduler.
	 */
	public void destroy() {
		try {
			scheduler.shutdown(true);

		} catch (SchedulerException e) {
			log.error("Error shutting down Scheduler", e);
			throw new RuntimeException(e.getCause());
		}
	}
}
