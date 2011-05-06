// Copyright(c) 2011 gTech, All Rights Reserved.

package com.gtech.iarc.ischedule.core.model;

import java.util.Date;

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
public class TaskExecutionAudit extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1535716246793732557L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	
	@Column(name = "TASK_EXE_ID")
    private Long taskId;
	
	@Column(name = "WHEN_START")
    private Date exeStartDate;
	
	@Column(name = "WHEN_COMPLETE")
    private Date completeDate;
	
	@Column(name = "STATUS")
    private String status;	
    
    @Column(name = "JOB_LOG")
    private String jobLog;
    
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Date getExeStartDate() {
		return exeStartDate;
	}
	public void setExeStartDate(Date exeStartDate) {
		this.exeStartDate = exeStartDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJobLog() {
		return jobLog;
	}
	public void setJobLog(String jobLog) {
		this.jobLog = jobLog;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;		
	} 
    
}
