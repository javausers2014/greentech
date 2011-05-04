// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core;


import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;


/**
 * Proxy Class for <b>org.quartz.Scheduler</b><br>
 * All schedule tasks' execution will be handled by Quartz framework.
 * @author ZHIDAO
 * @revision $Id$
 *
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
    public void start(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("Failed in starting Scheduler: " + e.getMessage());
            throw new RuntimeException(e.getCause());
        } 
    }


    /**
     * Start the schedule task using Quartz.
     * @param y3Job
     * @throws SchedulerException 
     * @throws ParseException 
     * @throws ParseException 
     */
    public void activateJob(AbstractSpringBeanTask task) throws SchedulerException, ParseException {
        
        if (task == null) return;
       
            JobDetail quartzJobDetail = new JobDetail();
            quartzJobDetail.setName(task.getTaskName());
            quartzJobDetail.setGroup(task.getTaskGroupName());
            quartzJobDetail.setJobClass(task.getClass());

            CronTrigger trigger = new CronTrigger();
            trigger.setName(task.getTaskName());
            trigger.setGroup(task.getTaskGroupName());
            trigger.setJobName(task.getTaskName());
            trigger.setJobGroup(task.getTaskGroupName());
            trigger.setStartTime(task.getStartTime().getTime());
            trigger.setEndTime(task.getEndTime().getTime());
            trigger.setCronExpression(task.getQuartzScheduledExpression());

            scheduler.scheduleJob(quartzJobDetail, trigger);
            log.debug("\n Quartz CronTrigger created for Task["
                + task.getTaskName() + "], Group["
                + task.getTaskGroupName() + "]\n");


    }

    /**
     * Cancel the excution of schedule task from Quartz
     * @param job
     */
    public void deleteJob(AbstractSpringBeanTask y3Job) throws SchedulerException {
		if (y3Job == null) return;
		
		
			scheduler.deleteJob(y3Job.getTaskName(), y3Job.getTaskGroupName());
			log.info("deleteJob - Successfully remove the job from Quartz");
		

	}

    /**
     * Suspend/Pause active schedule task from Quartz
     * @param job
     */
    public void pauseJob(String taskScheduleName, String taskGroupName) throws SchedulerException {

		
			scheduler.pauseTrigger(taskScheduleName, taskGroupName);
			scheduler.pauseJob(taskScheduleName, taskGroupName);
		
	}

    /**
     * Resume paused schedule task from Quartz
     * @param job
     */
    public void resumeJob(String taskScheduleName, String taskGroupName) throws SchedulerException {
		
			scheduler.resumeJob(taskScheduleName, taskGroupName);			
		
	}

    /**
     * Resschedule existing task from Quartz, regardless previous status of the task.
     * @param job
     */
    public void rescheduleJob(AbstractSpringBeanTask job) throws SchedulerException, ParseException {
        if (null == job) return;
            
			CronTrigger trigger = new CronTrigger(job.getTaskName(), job
					.getTaskGroupName());
			trigger.setJobGroup(job.getTaskGroupName());
			trigger.setJobName(job.getTaskName());
			trigger.setStartTime(job.getStartTime().getTime());
			trigger.setEndTime(job.getEndTime().getTime());
			trigger.setCronExpression(job.getQuartzScheduledExpression());
			scheduler.rescheduleJob(job.getTaskName(), job.getTaskGroupName(),
					trigger);

       
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
            log.error("Error shutting down Scheduler",e);
            throw new RuntimeException(e.getCause());
        }
    }
}
