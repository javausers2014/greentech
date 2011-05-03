package com.gtech.iarc.ischedule;

import org.quartz.JobExecutionContext;

/**
 * Implementing this interface could make the service class be executed @
 * scheduled time.
 * 
 * @author ZHIDAO
 */
public interface ScheduledWork {

	public void doIt(JobExecutionContext context);

}
