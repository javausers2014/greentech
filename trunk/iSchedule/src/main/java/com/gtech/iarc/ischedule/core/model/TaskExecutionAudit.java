// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;
/**
 * 
 * @author ZHIDAO
 */
public interface TaskExecutionAudit extends TaskBase{

	public Long getId();
	public void setId(Long id);

	/**
	 * @return Returns the jobTypeId.
	 */
	public Long getTaskId();

	/**
	 * @return Returns the jobLog.
	 */
	public String getJobLog();

	/**
	 * @return Returns the requestedBy.
	 */
	public String getRequestedBy();

	/**
	 * @return Returns the status.
	 */
	public String getStatus();


	/**
	 * @param jobId The jobId to set.
	 */
	public void setTaskId(Long jobId);

	/**
	 * @param jobLog The jobLog to set.
	 */
	public void setJobLog(String jobLog);

	/**
	 * @param requestedBy The requestedBy to set.
	 */
	public void setRequestedBy(String requestedBy);

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status);

	/**
	 * @return Returns the completeDate.
	 */
	public Timestamp getCompleteDate();

	/**
	 * @return Returns the exeStartDate.
	 */
	public Timestamp getExeStartDate();

	/**
	 * @param completeDate The completeDate to set.
	 */
	public void setCompleteDate(Timestamp completeDate);

	/**
	 * @param exeStartDate The exeStartDate to set.
	 */
	public void setExeStartDate(Timestamp exeStartDate);

}