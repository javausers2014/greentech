// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;

import com.gtech.iarc.ischedule.core.ScheduledWork;
import com.gtech.iarc.ischedule.service.ScheduleControlService;

/**
 * Abstract class implements scheduled service interface, used to execute scheduling job.<br>
 * 
 * @author ZHIDAO
 */
public abstract class AbstractScheduledWork implements ScheduledWork {

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
                ScheduleControlService.SCHEDULE_MODE_DAILYTIME)) {
            startDate.add(Calendar.DAY_OF_YEAR, -1);
        } else if (scheduleMode.trim().equalsIgnoreCase(
                ScheduleControlService.SCHEDULE_MODE_WEEKLY)) {
            startDate.add(Calendar.WEEK_OF_YEAR, -1);
        } else if (scheduleMode.trim().equalsIgnoreCase(
                ScheduleControlService.SCHEDULE_MODE_MONTHLY)) {
            startDate.add(Calendar.MONTH, -1);
        }else{
        	return null;
        }
        return startDate.getTime();
    }
	
    public void doIt(JobExecutionContext context) {
        String scheduledTaskName = context.getJobDetail().getName();
        doItNow(scheduledTaskName);
    }

    /**
     * All schedule
     * 
     * @param jobName
     * @param fromDate
     * @param endDate
     */
    public abstract void doItNow(String scheduledName);

}
