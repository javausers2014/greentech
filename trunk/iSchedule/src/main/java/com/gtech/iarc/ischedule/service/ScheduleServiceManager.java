// Copyright(c) 2007 Y3 Technologies Technologies, All Rights Reserved.
package com.gtech.iarc.ischedule.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.quartz.SchedulerException;

import com.gtech.iarc.ischedule.core.SchedulerControl;
import com.gtech.iarc.ischedule.core.ArcSchedulerConstants;
import com.gtech.iarc.ischedule.core.model.IArcTaskConfig;
import com.gtech.iarc.ischedule.core.model.IArcTaskDetail;
import com.gtech.iarc.ischedule.core.model.IArcTaskScheduleInfo;

/**
 * 
 * @author ZHIDAO
 * @revision $Id$
 */
public interface ScheduleServiceManager {
	
	public static final String ACTIVE_IND_ACTIVE = "Y";
	public static final String ACTIVE_IND_INACTIVE = "N";
	
    public static final String SCHEDULE_MODE_PERIODINTERVAL = "PERIOD_INTVL";

    public static final String SCHEDULE_MODE_ADHOC = "ADHOC";

    public static final String SCHEDULE_MODE_DAILYTIME = "DAILYTIME";

    public static final String SCHEDULE_MODE_WEEKLY = "WEEKLY";

    public static final String SCHEDULE_MODE_MONTHLY = "MONTHLY";

    public static final String SCHEDULE_MODE_YEARLY = "YEARLY";
    
    public static final String ACTIVE_JOB_STATUS = ArcSchedulerConstants.ACTIVE_JOB_STATUS;

    public static final String PAUSED_JOB_STATUS = ArcSchedulerConstants.PAUSED_JOB_STATUS;

//    public static final String DELETED_JOB_STATUS = "DELETED";

    public static final String COMPLETE_JOB_STATUS = "COMPLETED";
    
    public static final String EMPTY_RESULT_JOB_STATUS = "NO DATA";

    public static final String COMPLETE_WITH_ERROR_JOB_STATUS = "COMPLETED with ERROR";

    public static final String WIP_JOB_STATUS = "WIP";

    public static final String FAILED_JOB_STATUS = "FAILED";
    
    public static final String JOB_ACTIVE_IND = "Y";

    public static final String NJOB_ACTIVE_IND = "N";

    public static final String LOW_JOB_PRIORITY = "LOW";

    public static final String HIGH_JOB_PRIORITY = "HIGH";

    public static final String MEDIUM_JOB_PRIORITY = "MEDIUM";

    public static final String JRXML_FILE = "jrxml";

    public static final String CLASS_FILE = "class";

    public static final String MISMATCH = "MISMATCH";

    public static final String LOOKUP_LAYOUT_CLASS = "COMM_JOB_TYPE.OUTPUT_LAYOUT_TYPE";

    public static final String LOOKUP_PRIORITY_CLASS = "COMM_TASK.PRIORITY";

    public static final String LOOKUP_DEVICE_CLASS = "COMM_JOB.OUTPUT_DEVICE";

    public static final String LOOKUP_CLASS_WEEK_DAYS = "COMM_TASK.WEEK_DAYS";

    public static final String LOOKUP_CLASS_MONTHS = "COMM_TASK.MONTHS";

    public static final String LOOKUP_CLASS_MONTH_DAYS = "COMM_TASK.MONTH_DAYS";

    public static final String SCHEDULER_USERNAME = "SCHEDULER";

    public static final String DEFAULT_JOB_LISTENER = "DEFAULT_JOB_LISTENER";

    public static final String ERROR_INVALID_SCHEDULE = "scheduler.error.invalid.schhedule";

    /**
     * The task configed will be stored and then passed to scheduler.
     * After activate task, the status of Y3TaskConfig will be <br>
     * com.y3technologies.scheduler.core.Y3SchedulerConstants.ACTIVE_JOB_STATUS 
     * @param taskConfig
     * @see com.SchedulerControl.scheduler.core.ArcScheduler#activateJob(com.y3technologies.scheduler.core.Y3AbstractTask)
     */
    public void activateTask(IArcTaskConfig taskConfig);

    /**
     * The task will be stopped by scheduler first if it is still active, and then, 
     * the task will be removed from system.<br>
     * Once task is deleted, all information related to this task, such as parameter objects, running 
     * results, will all be removed from system.
     * 
     * @param taskConfig
     * @throws SchedulerException 
     * @see com.SchedulerControl.scheduler.core.ArcScheduler#activateJob(com.Arctechnologies.scheduler.core.ArcAbstractTask)
     */
    public void deleteTask(IArcTaskConfig taskConfig) throws SchedulerException;

    /**
     * The activate task will be paused by scheduler if it is still active.<br>
     * After activate task, the status of ArcTaskConfig will be <br>
     * com.Arctechnologies.scheduler.core.ArcSchedulerConstants.PAUSED_JOB_STATUS
     * 
     * @param activeTask
     * @throws SchedulerException 
     */
    public void pauseActiveTask(IArcTaskConfig activeTask) throws SchedulerException;

    /**
     * The paused task will be resumed by scheduler if it is still valid, i.e, still in valid period defined in ArcTaskConfig.<br>
     * After activate task, the status of ArcTaskConfig will be <br>
     * com.Arctechnologies.scheduler.core.ArcSchedulerConstants.ACTIVE_JOB_STATUS
     * @param pausedTask
     * @throws SchedulerException 
     */
    public void resumePausedTask(IArcTaskConfig pausedTask) throws SchedulerException;

    /**
     * The task will be rescheduled with new settings by scheduler if it is still valid.<br>
     * Not be opened as public API, since reschedule may involve "parameter object" changing.
     * Can ask user to suspend/delete old one and create a new one.
     */
    //public void reScheduleTask(IArcTaskConfig task);

    
    public List findActiveTaskConfigByUser(String userName);
    
    public List findActiveTaskConfigByGroup(String taskGroupName);
    
    public List findRunningTaskConfigByUser(String userName);

    public List findRunningTaskConfigByStatus(IArcTaskConfig taskConfig);

    public List findTaskConfig(String username);
    
    public List findTaskConfig(String taskDetailsName,String taskScheduleName);
    
    public IArcTaskConfig getTaskConfig(Long taskConfigId);
    
//    public void updateTaskDetail(IArcTaskDetail taskDetail);
    
    public List findTaskDetail(IArcTaskDetail taskDetail);

    public IArcTaskDetail getTaskDetail(String taskDetailShortName);
    
    public void saveTaskDetail(IArcTaskDetail taskDetail);

    /**
     * @param startDate
     * @param displayStartTime
     * @return
     * @throws ParseException 
     */
    public Date convertToDateTime(Date startDate, String displayStartTime) throws ParseException;

    /**
     * @param taskScheduleInfo
     * @return
     */
    public String convertToCronExpression(IArcTaskScheduleInfo taskScheduleInfo);
}
