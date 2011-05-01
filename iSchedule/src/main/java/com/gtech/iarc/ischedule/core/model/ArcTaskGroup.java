// Copyright(c) 2011 gTech, All Rights Reserved.

package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;

/**
 * @author ZHIDAO
 * @revision $Id$
 * @hibernate.class table="COMM_TASK_GROUP"
 */
public class ArcTaskGroup implements IArcTaskGroup {
	/* serialVersionUID */
	private static final long serialVersionUID = 4462821246534417484L;
	private Long taskGroupId;
    private String taskGroupShortName;
    private String taskGroupFullName;

    // Non-persistence members
    private String viewLink;
    private String updateLink;
    private String deleteLink;
    
	private Boolean activeInd;
    private String createdBy;
    private String modifiedBy;
    private String inactBy;    
    private Timestamp inactDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    
	/**
     *@return Returns the jobGroupFullName.
     *@hibernate.property column="TASK_GROUP_FULL_NAME" length="300"
     */
    public String getTaskGroupFullName() {
        return taskGroupFullName;
    }

    /**
     * @param jobGroupFullName The jobGroupFullName to set.
     */
    public void setTaskGroupFullName(String jobGroupFullName) {
        this.taskGroupFullName = jobGroupFullName;
    }

	/**
	 * @hibernate.id column="TASK_GROUP_ID" unsaved-value="null" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="SEQ_COMM_TASK_GROUP"
	 */
    public Long getTaskGroupId() {
        return taskGroupId;
    }

    /**
     * @param jobGroupId The jobGroupId to set.
     */
    public void setTaskGroupId(Long jobGroupId) {
        this.taskGroupId = jobGroupId;
    }

    /**
     *@return Returns the jobGroupShortName.
     *@hibernate.property column="TASK_GROUP_SHORT_NAME" length="60"
     */
    public String getTaskGroupShortName() {
        return taskGroupShortName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param jobGroupShortName DOCUMENT ME!
     */
    public void setTaskGroupShortName(String jobGroupShortName) {
        this.taskGroupShortName = jobGroupShortName;
    }

    // The following are Getter and Setter methods for non-persistence members

    /**
     * @return Returns the deleteLink.
     */
    public String getDeleteLink() {
        return deleteLink;
    }

    /**
     * @param deleteLink The deleteLink to set.
     */
    public void setDeleteLink(String deleteLink) {
        this.deleteLink = deleteLink;
    }

    /**
     * @return Returns the updateLink.
     */
    public String getUpdateLink() {
        return updateLink;
    }

    /**
     * @param updateLink The updateLink to set.
     */
    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    /**
     * @return Returns the viewLink.
     */
    public String getViewLink() {
        return viewLink;
    }

    /**
     * @param viewLink The viewLink to set.
     */
    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }
    
    /**
     * @return Returns the activeInd.
     * @hibernate.property column="ACTIVE_IND"
     */
    public Boolean getActiveInd() {
        return activeInd;
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
     * @hibernate.property column="MODIFIED_BY"
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @return Returns the modifiedDate.
     * @hibernate.property column="MODIFIED_DATE"
     */
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @param activeInd The activeInd to set.
     */
    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
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
