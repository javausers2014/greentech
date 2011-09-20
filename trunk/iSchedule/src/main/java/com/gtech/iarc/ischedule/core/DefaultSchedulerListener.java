package com.gtech.iarc.ischedule.core;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;



/**
 * Class receiving notification of events within the Scheduler itself<br>
 * - not necessarily events related to a specific trigger or job. <br><br>
 * Scheduler-related events include: 
 * <li>the addition,pause/resume and removal of a job/trigger
 * <li>a serious error within the scheduler
 * <li>notification of the scheduler being shutdown
 * <li>and others....
 * @author ZHIDAO
 * 
 */
public class DefaultSchedulerListener implements SchedulerListener {

	public void jobAdded(JobDetail jobDetail) {
		// TODO Auto-generated method stub
		
	}

	public void jobDeleted(JobKey jobKey) {
		// TODO Auto-generated method stub
		
	}

	public void jobPaused(JobKey jobKey) {
		// TODO Auto-generated method stub
		
	}

	public void jobResumed(JobKey jobKey) {
		// TODO Auto-generated method stub
		
	}

	public void jobScheduled(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	public void jobUnscheduled(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		
	}

	public void jobsPaused(String jobGroup) {
		// TODO Auto-generated method stub
		
	}

	public void jobsResumed(String jobGroup) {
		// TODO Auto-generated method stub
		
	}

	public void schedulerError(String msg, SchedulerException cause) {
		// TODO Auto-generated method stub
		
	}

	public void schedulerInStandbyMode() {
		// TODO Auto-generated method stub
		
	}

	public void schedulerShutdown() {
		// TODO Auto-generated method stub
		
	}

	public void schedulerShuttingdown() {
		// TODO Auto-generated method stub
		
	}

	public void schedulerStarted() {
		// TODO Auto-generated method stub
		
	}

	public void schedulingDataCleared() {
		// TODO Auto-generated method stub
		
	}

	public void triggerFinalized(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	public void triggerPaused(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		
	}

	public void triggerResumed(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		
	}

	public void triggersPaused(String triggerGroup) {
		// TODO Auto-generated method stub
		
	}

	public void triggersResumed(String triggerGroup) {
		// TODO Auto-generated method stub
		
	}
	
}
