package com.gtech.iarc.ischedule.core;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class DefaultJobListener implements JobListener{

	public String getName() {
		return "gTech Default Job Listener";
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println("jobExecutionVetoed : "+context.getJobDetail().getName());
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		System.out.println("jobToBeExecuted : "+context.getJobDetail().getName());
	}

	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		System.out.println("jobWasExecuted : "+context.getJobDetail().getName());
	}

}
