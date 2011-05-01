// Copyright(c) 2011 gTech, All Rights Reserved.

package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;

/**
 * @hibernate.class table="COMM_TASK_AUDIT"
 * @author ZHIDAO
 * @revision $Id$
 */
public class ArcTaskAudit implements IArcTaskAudit {
	/* serialVersionUID */
	private static final long serialVersionUID = -2281214982291787084L;
	private Long taskAuditId;
    private Long taskId;
    private Timestamp exeStartDate;
    private Timestamp completeDate;
    private String status;
    private String requestedBy;
    private String jobLog;

	/**
	 * @hibernate.id column="TASK_AUDIT_ID" unsaved-value="null" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="SEQ_COMM_TASK_AUDIT"
	 */
    public Long getTaskAuditId() {
        return taskAuditId;
    }

    /**
     * @return Returns the jobTypeId.
     * @hibernate.property column="TASK_ID"
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * @return Returns the jobLog.
     *  @hibernate.property column="JOB_LOG"
     */
    public String getJobLog() {
        return jobLog;
    }

    /**
     * @return Returns the requestedBy.
      * @hibernate.property column="REQUESTED_BY"
     */
    public String getRequestedBy() {
        return requestedBy;
    }

    /**
     * @return Returns the status.
      * @hibernate.property column="STATUS"
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param jobAuditId The jobAuditId to set.
     */
    public void setTaskAuditId(Long jobAuditId) {
        this.taskAuditId = jobAuditId;
    }

    /**
     * @param jobId The jobId to set.
     */
    public void setTaskId(Long jobId) {
        taskId = jobId;
    }

    /**
     * @param jobLog The jobLog to set.
     */
    public void setJobLog(String jobLog) {
        this.jobLog = jobLog;
    }

    /**
     * @param requestedBy The requestedBy to set.
     */
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the completeDate.
     * @hibernate.property column="COMPLETE_DATE"
     */
    public Timestamp getCompleteDate() {
        return completeDate;
    }

    /**
     * @return Returns the exeStartDate.
     *  @hibernate.property column="EXE_START_DATE"
     */
    public Timestamp getExeStartDate() {
        return exeStartDate;
    }

    /**
     * @param completeDate The completeDate to set.
     */
    public void setCompleteDate(Timestamp completeDate) {
        this.completeDate = completeDate;
    }

    /**
     * @param exeStartDate The exeStartDate to set.
     */
    public void setExeStartDate(Timestamp exeStartDate) {
        this.exeStartDate = exeStartDate;
    }
    
	private Boolean activeInd;
    private String createdBy;
    private String modifiedBy;
    private String inactBy;    
    private Timestamp inactDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate;    
    /**
     * @return Returns the activeInd.
     * @hibernate.property column="ACTIVE_IND"
     */
	public Boolean getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}

    /**
     * @return Returns the createdBy.
     * @hibernate.property column="CREATED_BY"
     *
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return Returns the createdDate.
     * @hibernate.property column="CREATED_DATE"
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @return Returns the inactBy.
     * @hibernate.property column="INACT_BY"
     */
    public String getInactBy() {
        return inactBy;
    }

    /**
     * @return Returns the inactDate.
     * @hibernate.property column="INACT_DATE"
     */
    public Timestamp getInactDate() {
        return inactDate;
    }

    /**
     * @return Returns the modifiedBy.
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @return Returns the modifiedDate.
     */
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @param createdDate The createdDate to set.
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @param inactBy The inactBy to set.
     */
    public void setInactBy(String inactBy) {
        this.inactBy = inactBy;
    }

    /**
     * @param inactDate The inactDate to set.
     */
    public void setInactDate(Timestamp inactDate) {
        this.inactDate = inactDate;
    }

    /**
     * @param modifiedBy The modifiedBy to set.
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @param modifiedDate The modifiedDate to set.
     */
    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
