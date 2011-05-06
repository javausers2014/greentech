// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.service;

/**
 * @author ZHIDAO
 */
public interface ScheduleConstants {
   
    public static final String JOB_STATUS_ACTIVE = "ACTIVE";

    public static final String JOB_STATUS_PAUSED = "PAUSED";

    public static final String JOB_STATUS_COMPLETE = "COMPLETED";

    public static final String JOB_STATUS_COMPLETEWITHERROR = "COMPLETED_ERROR";

    public static final String JOB_STATUS_WORKINGINPROCESS = "WIP";
    
    public static final String JOB_STATUS_DELETED = "DELETED";

    public static final String SCHEDULER_USERNAME = "Arc_Scheduler";
    
    public static final String DEFAULT_JOB_GROUP = "GTECH_SCHEDULER_GRP";
    
    public static final String DEFAULT_JOB_LISTENER = "DEFAULT_JOB_LISTENER";
    
    public static final String SCHEDULE_MODE_PERIODINTERVAL = "PERIOD_INTVL";

    public static final String SCHEDULE_MODE_ADHOC = "ADHOC";

    public static final String SCHEDULE_MODE_DAILYTIME = "DAILYTIME";

    public static final String SCHEDULE_MODE_WEEKLY = "WEEKLY";

    public static final String SCHEDULE_MODE_MONTHLY = "MONTHLY";

    public static final String SCHEDULE_MODE_YEARLY = "YEARLY";    
    
    public static final String JOB_STATUS_EMPTY_RESULT = "COMPLETE_WITHOUT_DATA";
    
    public static final String JOB_STATUS_FAILED = "FAILED";
    
    public static final String JOB_ACTIVE_IND_TRUE = "Y";

    public static final String JOB_ACTIVE_IND_FALSE = "N";

    public static final String JOB_PRIORITY_LOW = "LOW";

    public static final String JOB_PRIORITY_HIGH = "HIGH";

    public static final String JOB_PRIORITY_MEDIUM = "MEDIUM";    
}
