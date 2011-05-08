package com.gtech.iarc.ischedule;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.gtech.iarc.ischedule.context.SpringBeanServiceDelegator;

/**
 * Abstract class defining all APIs and common functions needed for executing scheduled task by the scheduler.<br>
 * 
 * Scheduler <b>ONLY</b> recognize task extending from this class, where <b>org.quartz.Job</b> is implemented. <br>
 * 
 * <li>Instances of <b>org.quartz.Job</b> must have a public no-argument constructor.<br>
 * 
 * The full class name of the concreted class extending from this class <br>
 * will be populated to the attribute of <i>taskDetailJavaClass</i> of <code>com.gtech.iarc.ischedule.core.model.TaskExecutionDetail</code><br>
 * 
 * <li>Each TaskExecutionDetail could be configed with different time and parameter(defined in TaskScheduleRequirementContext), one TaskExecutionDetail -- multiple TaskScheduleRequirementContext.<br>
 * <li>Each TaskExecutionDetail MUST PROVIDE one concreted class extending from AbstractSpringBeanTask.<br>
 * <p>
 * Scheduler will hold the instance of concreted class of AbstractSpringBeanTask. <br>
 * Once it's time to execute task, scheduler will invoke AbstractSpringBeanTask.executeInternal(JobExecutionContext),<br>
 * where invocation delegated to SpringBeanServiceDelegator.processScheduleJob(JobExecutionContext, proxyBeanName).<br>
 * In SpringBeanServiceDelegator.processScheduleJob(JobExecutionContext, proxyBeanName), using spring application context, the implementation of <i>ScheduledWork</i>
 * will be loaded by bean name i.e. proxyBeanName, and doIt(JobExecutionContext) will be invoked.
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public abstract class AbstractSpringBeanTask extends QuartzJobBean {
	
    /**
     * Return the spring bean ID of proxy service, providing to SpringBeanServiceDelegator to load the service.
     * 
     * @return String the spring bean name for executing task.
     */
    public abstract String getProxyBeanName();
    

    /**
     * Pass the job to proxy.
     * 
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     * @param context
     */
    protected void executeInternal(JobExecutionContext context) {      
        SpringBeanServiceDelegator.processScheduleJob(context, this.getProxyBeanName());
    }
    
    public AbstractSpringBeanTask() {}
//
//    protected String taskName;
//
//    protected final String taskGroupName = "GTECH_JOBGRP";
//
//    protected Calendar startTime;
//
//    protected Calendar endTime;
//
//    protected String quartzScheduledExpression;
//
//
//    /**
//     * @return
//     */
//    public Calendar getEndTime() {
//        return endTime;
//    }
//
//    /**
//     * 
//     * @param endTime
//     */
//    public void setEndTime(Calendar endTime) {
//        this.endTime = endTime;
//    }
//
//    /**
//     * 
//     * @return
//     */
//    public String getQuartzScheduledExpression() {
//        return quartzScheduledExpression;
//    }
//
//    /**
//     * 
//     * @param scheduledExpress
//     */
//    public void setQuartzScheduledExpression(String scheduledExpress) {
//        this.quartzScheduledExpression = scheduledExpress;
//    }
//
//    /**
//     * 
//     * @return
//     */
//    public Calendar getStartTime() {
//        return startTime;
//    }
//
//    /**
//     * 
//     * @param startTime
//     */
//    public void setStartTime(Calendar startTime) {
//        this.startTime = startTime;
//    }
//
//    /**
//     * 
//     * @return
//     */
//    public String getTaskName() {
//        return this.taskName;
//    }
//
//    /**
//     * 
//     * @return
//     */
//    public String getTaskGroupName() {
//        return this.taskGroupName;
//    }
//
//    public void setTaskName(String jobName) {
//        this.taskName = jobName;
//    }
}
