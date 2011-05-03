// Copyright(c) 2011 gTech, All Rights Reserved.

package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;

import com.gtech.iarc.ischedule.core.TaskExecutionAudit;

/**
 * @author ZHIDAO
 * @revision $Id$
 */
public class DefaultTaskExecutionAudit implements TaskExecutionAudit {
	/* serialVersionUID */
	private static final long serialVersionUID = -2281214982291787084L;
	private Long taskAuditId;
    private Long taskId;
    private Timestamp exeStartDate;
    private Timestamp completeDate;
    private String status;
    private String requestedBy;
    private String jobLog;
	private Boolean activeInd;
    private String createdBy;
    private Timestamp createdDate;    
    private String modifiedBy;
    private Timestamp modifiedDate;
	public Long getTaskAuditId() {
		return taskAuditId;
	}
	public void setTaskAuditId(Long taskAuditId) {
		this.taskAuditId = taskAuditId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Timestamp getExeStartDate() {
		return exeStartDate;
	}
	public void setExeStartDate(Timestamp exeStartDate) {
		this.exeStartDate = exeStartDate;
	}
	public Timestamp getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Timestamp completeDate) {
		this.completeDate = completeDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getJobLog() {
		return jobLog;
	}
	public void setJobLog(String jobLog) {
		this.jobLog = jobLog;
	}
	public Boolean getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	} 
    
}
