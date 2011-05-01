// Copyright(c) 2011 gTech, All Rights Reserved.

package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;

/**
 * @author ZHIDAO
 * @revision $Id$
 * @hibernate.class table="COMM_TASK_DETAIL"
 */
public class ArcTaskDetail implements IArcTaskDetail {	
	/* serialVersionUID */
	private static final long serialVersionUID = 3059857105280521073L;
	private Long taskDetailId;
    private Long taskGroupId;
    private String taskDetailShortName;
    private String taskDetailFullName;
    private String taskDetailJavaClass;
    private String taskDetailCategory;
    private String parameterJsp;
    private String taskObjectClass;
    private IArcTaskGroup ArcTaskGroup;
    private String taskObjectCode;
    private String role;
	private Boolean activeInd;
    private String createdBy;
    private String modifiedBy;
    private String inactBy;    
    private Timestamp inactDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate; 

    /**
     * @hibernate.property column="TASK_CATEGORY"
     */
    public String getTaskDetailCategory() {
        return taskDetailCategory;
    }

    public void setTaskDetailCategory(String taskDetailCategory) {
        this.taskDetailCategory = taskDetailCategory;
    }    

    /**
    * @return Returns the jobJavaClass.
    * @hibernate.property column="TASK_GROUP_ID"
    */
    public Long getTaskGroupId() {
        return taskGroupId;
    }

    /**
     * @return Returns the jobJavaClass.
     * @hibernate.property column="TASK_DETAIL_JAVA_CLASS"
     */
    public String getTaskDetailJavaClass() {
        return taskDetailJavaClass;
    }

    /**
     * @return Returns the jobTypeFullName.
     * @hibernate.property column="TASK_DETAIL_FULL_NAME"
     */
    public String getTaskDetailFullName() {
        return taskDetailFullName;
    }

	/**
	 * @hibernate.id column="TASK_DETAIL_ID" unsaved-value="null" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="SEQ_COMM_TASK_DETAIL"
	 */
    public Long getTaskDetailId() {
        return taskDetailId;
    }

    /**
     * @return Returns the jobTypeShortName.
     * @hibernate.property column="TASK_DETAIL_SHORT_NAME" unique="true"
     */
    public String getTaskDetailShortName() {
        return taskDetailShortName;
    }


    /**
     * @return Returns the parameterJsp.
     * @hibernate.property column="PARAMETER_JSP"
     */
    public String getParameterJsp() {
        return parameterJsp;
    }


    /**
     * @param taskGroupId The taskGroupId to set.
     */
    public void setTaskGroupId(Long taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    /**
     * @param taskDetailJavaClass The taskDetailJavaClass to set.
     */
    public void setTaskDetailJavaClass(String jobTypeJavaClass) {
        this.taskDetailJavaClass = jobTypeJavaClass;
    }

    /**
     * @param taskDetailFullName The taskDetailFullName to set.
     */
    public void setTaskDetailFullName(String taskDetailFullName) {
        this.taskDetailFullName = taskDetailFullName;
    }

    /**
     * @param taskDetailId The taskDetailId to set.
     */
    public void setTaskDetailId(Long taskDetailId) {
        this.taskDetailId = taskDetailId;
    }

    /**
     * @param taskDetailShortName The taskDetailShortName to set.
     */
    public void setTaskDetailShortName(String jobTypeShortName) {
        this.taskDetailShortName = jobTypeShortName;
    }


    /**
     * @param parameterJsp The parameterJsp to set.
     */
    public void setParameterJsp(String parameterJsp) {
        this.parameterJsp = parameterJsp;
    }

    /**
     * @return Returns the taskGroup.
     * @hibernate.many-to-one column="TASK_GROUP_ID"
     * class="com.gtech.iarc.ishedule.model.ArcTaskGroup" not-null="false" insert="false" update="false"
     */
    public IArcTaskGroup getTaskGroup() {
        return ArcTaskGroup;
    }

    /**
     * @param taskGroup The taskGroup to set.
     */
    public void setTaskGroup(IArcTaskGroup taskGroup) {
        this.ArcTaskGroup = taskGroup;
    }


    /**
     * @return Returns the jobTypeObjectClass.
     * @hibernate.property column="TASK_OBJECT_CLASS"
     */
    public String getTaskObjectClass() {
        return taskObjectClass;
    }
    public void setTaskObjectClass(String taskObjectClass) {
        this.taskObjectClass = taskObjectClass;
    }

    /** @hibernate.property column="TASK_OBJECT_CODE" */
	public String getTaskObjectCode() {
		return taskObjectCode;
	}
	public void setTaskObjectCode(String taskObjectCode) {
		this.taskObjectCode = taskObjectCode;
	}

	/**
	 *@hibernate.property column="ROLE_ID"
	 */
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
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
