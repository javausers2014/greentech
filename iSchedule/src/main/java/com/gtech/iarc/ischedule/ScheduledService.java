package com.gtech.iarc.ischedule;

import org.quartz.JobExecutionContext;

/**
 * Implementing this interface could make the service class be executed @
 * scheduled time.
 * 
 * @author ZHIDAO
 */
public interface ScheduledService {

	public void processJob(JobExecutionContext context);

}
