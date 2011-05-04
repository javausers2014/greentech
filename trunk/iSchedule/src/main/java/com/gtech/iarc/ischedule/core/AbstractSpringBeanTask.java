package com.gtech.iarc.ischedule.core;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.gtech.iarc.ischedule.service.context.SpringBeanServiceDelegator;

/**
 * Abstract class defining all APIs and common functions needed for executing sheduled task by the scheduler.<br>
 * 
 * Scheduler <b>ONLY</b> recognize Y3 task extending from this class, where <b>org.quartz.Job</b> is implemented. <br>
 * 
 * <li>Instances of <b>org.quartz.Job</b> must have a public no-argument constructor.<br>
 * <li>The extending concreted class must have a public two-arguments constructor of (String taskName, String jobId)<br>
 * 
 * The full class name of the concreted class extending from this class <br>
 * will be populated to the attribute of <i>taskDetailJavaClass</i> of com.y3technologies.scheduler.model.Y3TaskDetail<br>
 * 
 * <li>Each Y3TaskDetail could be configed with different time and parameter, i.e., one Y3TaskDetail -- multiple Y3TaskConfig.<br>
 * <li>Each Y3TaskDetail only associated with one concreted class of Y3AbstractTask.<br>
 * <p>
 * Scheduler will hold the instance of concreted class of Y3AbstractTask. <br>
 * Once it's time to execute task, scheduler will call Y3AbstractTask.executeInternal(JobExecutionContext),<br>
 * then Y3ScheduleServiceDelegator.processScheduleJob(JobExecutionContext, String) <br>
 * will be invoked to load spring bean implementing <i>com.y3technologies.scheduler.IY3ScheduleServiceManager</i>
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public abstract class AbstractSpringBeanTask extends QuartzJobBean {

    public abstract String getProxyLookupBean();
    
    private static Log log = LogFactory.getLog(AbstractSpringBeanTask.class);

    /**
     * Pass the job to proxy.
     * 
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     * @param context
     */
    protected void executeInternal(JobExecutionContext context) {
        log.debug("Job is to be excuted by [" + context.getJobDetail().getName()+ "]");
      
        SpringBeanServiceDelegator.processScheduleJob(context, this.getProxyLookupBean());
        
        log.debug("Job has been finished by [" + context.getJobDetail().getName() + "]\n");
    }

    /**
     * Return the proxy name to service delegator to load the service.
     * 
     * @return String the spring bean name for executing task.
     */


    protected static Log logger = LogFactory.getLog(AbstractSpringBeanTask.class);

    protected String taskName;

    protected final String taskGroupName = "GTECH_JOBGRP";

    protected Calendar startTime;

    protected Calendar endTime;

    protected String quartzScheduledExpression;


    /**
     * @return
     */
    public Calendar getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime
     */
    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    /**
     * 
     * @return
     */
    public String getQuartzScheduledExpression() {
        return quartzScheduledExpression;
    }

    /**
     * 
     * @param scheduledExpress
     */
    public void setQuartzScheduledExpression(String scheduledExpress) {
        this.quartzScheduledExpression = scheduledExpress;
    }

    /**
     * 
     * @return
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime
     */
    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * @return
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * 
     * @return
     */
    public String getTaskGroupName() {
        return this.taskGroupName;
    }

    public void setTaskName(String jobName) {
        this.taskName = jobName;
    }
}
