// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;

import com.gtech.iarc.ischedule.service.ScheduleServiceManager;

/**
 * Abstract class implements scheduled service interface, used to execute scheduling job.<br>
 * 
 * @author ZHIDAO
 */
public abstract class AbstractScheduledService implements ScheduledService {

    /**
     * Tracing back the last execution time.
     * 
     * Not valid for ADHOC and PERIOD INTERVAL mode
     * @param scheduleMode
     * @param endDate
     * @return
     */
    protected Date getBackwardStartDateByScheduleMode(String scheduleMode, Date endDate){

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(endDate);
        
        if (scheduleMode.trim().equalsIgnoreCase(
                ScheduleServiceManager.SCHEDULE_MODE_DAILYTIME)) {
            startDate.add(Calendar.DAY_OF_YEAR, -1);
        } else if (scheduleMode.trim().equalsIgnoreCase(
                ScheduleServiceManager.SCHEDULE_MODE_WEEKLY)) {
            startDate.add(Calendar.WEEK_OF_YEAR, -1);
        } else if (scheduleMode.trim().equalsIgnoreCase(
                ScheduleServiceManager.SCHEDULE_MODE_MONTHLY)) {
            startDate.add(Calendar.MONTH, -1);
        }else{
        	return null;
        }
        return startDate.getTime();
    }
	
    public void processJob(JobExecutionContext context) {
        String scheduledTaskName = context.getJobDetail().getName();
        processJob(scheduledTaskName);
    }

    /**
     * All schedule
     * 
     * @param jobName
     * @param fromDate
     * @param endDate
     */
    public abstract void processJob(String scheduledName);

}
