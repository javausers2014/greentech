// Copyright(c) 2011 gTech, All Rights Reserved.

package com.gtech.iarc.ischedule.core.model;

import java.sql.Timestamp;

/**
 * @author ZHIDAO
 */
public class DefaultTaskExecutionDetail implements TaskExecutionDetail {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3318043902640447328L;
	private Long id;
	private String taskDetailCode;
	private String taskDetailDesc;
	private String taskDetailJavaClass;
	private String taskDetailCategory;
	private String parameterJsp;
	private String taskObjectClass;
	private String taskGroupCode;
	private String taskObjectCode;
	private String role;
	private Boolean activeInd;
	private String createdBy;
	private String modifiedBy;
	private Timestamp createdDate;
	private Timestamp modifiedDate;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskDetailCode() {
		return taskDetailCode;
	}

	public void setTaskDetailCode(String taskDetailShortName) {
		this.taskDetailCode = taskDetailShortName;
	}

	public String getTaskDetailDesc() {
		return taskDetailDesc;
	}

	public void setTaskDetailDesc(String taskDetailFullName) {
		this.taskDetailDesc = taskDetailFullName;
	}

	public String getTaskDetailJavaClass() {
		return taskDetailJavaClass;
	}

	public void setTaskDetailJavaClass(String taskDetailJavaClass) {
		this.taskDetailJavaClass = taskDetailJavaClass;
	}

	public String getTaskDetailCategory() {
		return taskDetailCategory;
	}

	public void setTaskDetailCategory(String taskDetailCategory) {
		this.taskDetailCategory = taskDetailCategory;
	}

	public String getParameterJsp() {
		return parameterJsp;
	}

	public void setParameterJsp(String parameterJsp) {
		this.parameterJsp = parameterJsp;
	}

	public String getTaskObjectClass() {
		return taskObjectClass;
	}

	public void setTaskObjectClass(String taskObjectClass) {
		this.taskObjectClass = taskObjectClass;
	}

	public String getTaskGroupCode() {
		return taskGroupCode;
	}

	public void setTaskGroupCode(String taskGroupCode) {
		this.taskGroupCode = taskGroupCode;
	}

	public String getTaskObjectCode() {
		return taskObjectCode;
	}

	public void setTaskObjectCode(String taskObjectCode) {
		this.taskObjectCode = taskObjectCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
