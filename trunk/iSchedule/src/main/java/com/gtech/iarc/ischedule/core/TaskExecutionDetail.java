// Copyright(c) 2011 gTech, All Rights Reserved.
package com.gtech.iarc.ischedule.core;

/**
 * 
 * @author ZHIDAO
 * @revision $Id: IArcTaskDetail.java 6647 2007-08-21 15:11:13Z zhidao $
 */
public interface TaskExecutionDetail extends TaskBase{
	
	public static final String TASK_CATEGORY_REPORT="REPORT";
	public static final String TASK_CATEGORY_INTEGRATION="INTEGRATION";
	public static final String TASK_CATEGORY_APPAUTO="APP_AUTO";
	
	public String getTaskDetailCategory();

	public void setTaskDetailCategory(String taskDetailCategory);

	public String getTaskDetailJavaClass();

	public String getTaskDetailFullName();

	public Long getTaskDetailId();

	public String getTaskDetailShortName();

	public String getParameterJsp();

	public void setTaskDetailJavaClass(String taskDetailJavaClass);

	public void setTaskDetailFullName(String jobTypeFullName);

	public void setTaskDetailId(Long jobTypeId);

	public void setTaskDetailShortName(String jobTypeShortName);

	public void setParameterJsp(String parameterJsp);

	public String getTaskGroupCode();

	public void setTaskGroupCode(String taskGroupCode);

	public String getTaskObjectClass();
	
	public void setTaskObjectClass(String taskObjectClass);

	public String getTaskObjectCode();

	public void setTaskObjectCode(String taskObjectCode);

	public String getRole();

	public void setRole(String role);

}