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
 */
@Entity
@Table(name = "TASK_EXE_DETAIL")
public class TaskExecutionDetail extends BaseObject {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3318043902640447328L;
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "CODE")
	private String taskDetailCode;
	
	@Column(name = "DESC")
	private String taskDetailDesc;
	
	@Column(name = "CLASSNAME")
	private String taskDetailJavaClass;
	
	@Column(name = "CATEGORY")
	private String taskDetailCategory;
	
	private String parameterJsp;
	
	@Column(name = "ARGUMENT_CLASS")
	private String taskObjectClass;
	
	@Column(name = "GROUP_CODE")
	private String taskGroupCode;
	
	private String taskObjectCode;
	private String role;
	
	@Column(name = "ACTIVE")
	private Boolean activeInd;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
