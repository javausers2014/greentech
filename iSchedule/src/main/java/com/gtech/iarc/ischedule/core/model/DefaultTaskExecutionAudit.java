// Copyright(c) 2011 gTech, All Rights Reserved.

package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gtech.iarc.base.model.core.BaseObject;


/**
 * @author ZHIDAO
 * @revision $Id$
 */
@Entity
@Table(name = "TASK_EXE_AUDIT")
public class DefaultTaskExecutionAudit extends BaseObject implements TaskExecutionAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1535716246793732557L;

	//private Long taskAuditId;
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	
	@Column(name = "TASK_EXE_ID")
    private Long taskId;
	
	@Column(name = "WHEN_START")
    private Timestamp exeStartDate;
	
	@Column(name = "WHEN_COMPLETE")
    private Timestamp completeDate;
	
	@Column(name = "STATUS")
    private String status;
	
	
    private String requestedBy;
    
    @Column(name = "JOB_LOG")
    private String jobLog;
    
    
	private Boolean activeInd;
    
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

    private String modifiedBy;
    private Timestamp modifiedDate;
//	public Long getTaskAuditId() {
//		return taskAuditId;
//	}
//	public void setTaskAuditId(Long taskAuditId) {
//		this.taskAuditId = taskAuditId;
//	}
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;		
	} 
    
}
